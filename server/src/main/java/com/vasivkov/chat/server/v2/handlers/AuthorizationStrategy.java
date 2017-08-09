package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.common.AuthorizationRequest;
import com.vasivkov.chat.common.GeneralResponse;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageResponse;
import com.vasivkov.chat.server.User;
import com.vasivkov.chat.server.dao.MessageDao;
import com.vasivkov.chat.server.dao.UserDao;
import com.vasivkov.chat.server.v2.Server;
import com.vasivkov.chat.server.v2.vo.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthorizationStrategy implements Strategy<AuthorizationRequest> {
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
                responses.add(new ResponseWithRecipients(id, new GeneralResponse(true, "You are registrated!")));
                responses.addAll(Server.getLastLetters(new MessageDao().getLastTenMessages(), id));
                Server.getConnectedClients().get(id).setAuthorized(true);
                LOGGER.info(id + " - " + login + ": Connected to chat");
                responses.add(new ResponseWithRecipients(Server.getAuthorizedClients(id), new MessageResponse(new Message(login, "I'm IN CHAT", new Date()))));
                return responses;
            }
        } catch (SQLException e) {
            responses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Please, try again later!")));
            LOGGER.error("Failed to read data from database");
            return responses;
        }

    }
}

