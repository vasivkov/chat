package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Date;

public class MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());
    private static ChatDao chatDao = new ChatDao();

    public static Response handlerOfRequest(Request request) {
        try {
            if (request instanceof AuthorizationRequest) {
                return method1((AuthorizationRequest) request);
            }
            if (request instanceof RegistrationRequest) {
                return method2((RegistrationRequest) request);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to handle request " + request, e);
            return FactoryResponse.getServerErrorResponse();
        }
        throw new IllegalArgumentException("Received message of unknown type " + request);
    }

    private static Response method1(AuthorizationRequest request) throws SQLException {
        String login = request.getLogin();
        String password = request.getPassword();
        User user = chatDao.findByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            LOGGER.info("Client " + login + " autorizated");
            return FactoryResponse.getAuthorizedResponse();
        } else {
            return FactoryResponse.getNotAutorizedResponse();
        }
    }

    private static Response method2(RegistrationRequest request) throws SQLException {
        String login = request.getLogin();
        String password = request.getPassword();
        if (chatDao.findByLogin(login) != null) {
            return FactoryResponse.getNotRegisteredResponse();
        } else {
            User user = new User(login, password, new Date());
            chatDao.insertUser(user);
            return FactoryResponse.getRegisteredResponse();
        }
    }
}
