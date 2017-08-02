package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.server.MessageHandler;
import com.vasivkov.chat.server.User;
import com.vasivkov.chat.server.dao.UserDao;
import com.vasivkov.chat.server.v2.ServerConnectionV2;
import com.vasivkov.chat.server.v2.ServerV2;
import com.vasivkov.chat.server.v2.vo.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthorizationStrategy implements Strategy<AuthorizationRequest> {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());
    private static UserDao userDao = new UserDao();
    private ServerConnectionV2 serverConnectionV2;


    @Override
    public List<ResponseWithRecipients> process(AuthorizationRequest authorizationRequest) {
        String login = authorizationRequest.getLogin();
        String password = authorizationRequest.getPassword();
        int id = authorizationRequest.getId();
        List<ResponseWithRecipients> listOfResponses = new ArrayList<>();
        List<Integer> listOfResipients = new ArrayList<>();
        for (Map.Entry<Integer, ServerConnectionV2> pair : ServerV2.getConnectedClients().entrySet()) {
            if (pair.getKey() != authorizationRequest.getId() && pair.getValue().isActive()) {
                listOfResipients.add(pair.getKey());
            }
            try {
                User user = userDao.findByLogin(login);
                if (user == null) {
                    listOfResponses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Invalid login!")));
                    return listOfResponses;
                } else if (!password.equals(user.getPassword())) {
                    listOfResponses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Invalid password!")));
                    return listOfResponses;
                } else {
                    ServerV2.getConnectedClients().get(id).setActive(true);
                    ResponseWithRecipients backResponse = new ResponseWithRecipients(id, new GeneralResponse(true, "You are registrated!"));
                    ResponseWithRecipients responseToAll = new ResponseWithRecipients(listOfResipients, new MessageResponse(new Message("    " + login + " connected to chat!")));
                    listOfResponses.add(backResponse);
                    listOfResponses.add(responseToAll);
                    return listOfResponses;
                }
            } catch (SQLException e) {
                listOfResponses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Please, try again later!")));
                return listOfResponses;
            }

        }

        return listOfResponses;
    }
}

