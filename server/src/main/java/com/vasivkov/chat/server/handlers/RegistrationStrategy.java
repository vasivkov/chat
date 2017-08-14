package com.vasivkov.chat.server.handlers;

import com.vasivkov.chat.common.GeneralResponse;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageResponse;
import com.vasivkov.chat.common.RegistrationRequest;
import com.vasivkov.chat.server.User;
import com.vasivkov.chat.server.dao.UserDao;
import com.vasivkov.chat.server.Server;
import com.vasivkov.chat.server.vo.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrationStrategy extends AbstractStrategy implements Strategy<RegistrationRequest> {
    private static final Logger LOGGER = Logger.getLogger(RegistrationStrategy.class.getName());
    private UserDao userDao = new UserDao();

    @Override
    public List<ResponseWithRecipients> process(RegistrationRequest request) {
        String login = request.getLogin();
        int id = request.getId();
        List<ResponseWithRecipients> responses = new ArrayList<>();
        try {
            User user = userDao.findByLogin(login);
            if (user == null) {
               return registeredNewUser(request.getLogin(), request.getPassword(), request.getId());
            } else {
                responses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Such login already exits!")));
                return responses;
            }
        } catch (SQLException e) {
            responses.add(new ResponseWithRecipients(id, new GeneralResponse(false, "Please, try again later!")));
            LOGGER.error("Failed to read data from database");
            return responses;
        }
    }
    private List<ResponseWithRecipients> registeredNewUser(String login, String password, int id) throws SQLException{
        List<ResponseWithRecipients> responses = new ArrayList<>();
        Server.getConnectedClients().get(id).setAuthorized(true);
        LOGGER.info(id + " - " + login + ": Connected to chat");
        userDao.insertUser(new User(login, password, new Date()));
        responses.add(new ResponseWithRecipients(id, new GeneralResponse(true, "You are registrated!")));
        responses.addAll(getLastMessages(id));
        List<Integer> authorizedClients = Server.getAuthorizedClients(id);
        if (!authorizedClients.isEmpty()) {
            responses.add(new ResponseWithRecipients(authorizedClients, new MessageResponse(new Message(login, "I'm IN CHAT", new Date()))));
        }

        return responses;
    }
}
