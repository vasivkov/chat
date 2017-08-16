package com.vasivkov.chat.server.handlers;

import com.vasivkov.chat.common.AuthorizationRequest;
import com.vasivkov.chat.common.GeneralResponse;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageResponse;
import com.vasivkov.chat.server.User;
import com.vasivkov.chat.server.dao.UserDao;
import com.vasivkov.chat.server.Server;
import com.vasivkov.chat.server.vo.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

public class AuthorizationStrategy extends AbstractStrategy implements Strategy<AuthorizationRequest> {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationStrategy.class.getName());

    private UserDao userDao = new UserDao();

    @Override
    public List<ResponseWithRecipients> process(AuthorizationRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        int id = request.getId();
        LOGGER.info("login = " + login + ": id = " + id);
        List<ResponseWithRecipients> responses = new ArrayList<>();
        try {
            User user = userDao.findByLogin(login);
            if (user == null) {
                responses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Invalid login!")));
                return responses;
            } else if (!password.equals(user.getPassword())) {
                responses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Invalid password!")));
                return responses;
            } else {
               return authorizedNewUser(login, id);
            }
        } catch (SQLException e) {
            responses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Please, try again later!")));
            LOGGER.error("Failed to read data from database");
            return responses;
        }

    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    private List<ResponseWithRecipients> authorizedNewUser(String login, int id){
        List<ResponseWithRecipients> responses = new ArrayList<>();
        responses.add(new ResponseWithRecipients(id, new GeneralResponse(true, "You are registered!")));
        responses.addAll(getLastMessages(id));
        Server.getConnectedClients().get(id).setAuthorized(true);
        LOGGER.info(id + " - " + login + ": Connected to chat");
        List<Integer> authorizedClients = Server.getAuthorizedClients(id);
        if (!authorizedClients.isEmpty()) {
            responses.add(new ResponseWithRecipients(authorizedClients, new MessageResponse(new Message(login, "I'm IN CHAT", new Date()))));
        }
        return responses;
    }
}

