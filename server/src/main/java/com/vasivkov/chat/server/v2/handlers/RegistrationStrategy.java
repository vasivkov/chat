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
import java.util.Date;
import java.util.List;
import java.util.Map;

public class
RegistrationStrategy implements Strategy<RegistrationRequest> {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());
    private static UserDao userDao = new UserDao();

    @Override
    public List<ResponseWithRecipients> process(RegistrationRequest registrationRequest) {
        String login = registrationRequest.getLogin();
        String password = registrationRequest.getPassword();
        int id = registrationRequest.getId();
        List<ResponseWithRecipients> listOfresponses = new ArrayList<>();
        List<Integer> listOfResipients = new ArrayList<>();
        for (Map.Entry<Integer, ServerConnectionV2> pair : ServerV2.getConnectedClients().entrySet()) {
            if (pair.getKey() != registrationRequest.getId() && pair.getValue().isActive()) {
                listOfResipients.add(pair.getKey());
            }
        }
        try {
            User user = userDao.findByLogin(login);
            if (user == null) {
                ServerV2.getConnectedClients().get(id).setActive(true);
                userDao.insertUser(new User(login, password, new Date()));
                ResponseWithRecipients backResponse = new ResponseWithRecipients(id, new GeneralResponse(true, "You are registrated!"));
                ResponseWithRecipients responseToAll = new ResponseWithRecipients(listOfResipients, new MessageResponse(new Message("    " + login + " connected to chat!")));
                listOfresponses.add(backResponse);
                listOfresponses.add(responseToAll);
                return listOfresponses;
            } else {
                listOfresponses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Such login already exits!")));
                return listOfresponses;
            }

        } catch (SQLException e) {
            listOfresponses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Please, try again later!")));
            return listOfresponses;
        }
    }
}
