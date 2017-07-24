package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;
import com.vasivkov.chat.server.dao.UserDao;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Date;

public class MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());
    private static UserDao userDao = new UserDao();

    public static Response handle(Request request) {
        try {
            if (request instanceof AuthorizationRequest) {
                return handleAutorizationRequest((AuthorizationRequest) request);
            }
            if (request instanceof RegistrationRequest) {
                return handleRegistrationRequest((RegistrationRequest) request);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to handle request " + request, e);
            return FactoryResponse.getServerErrorResponse();
        }
        throw new IllegalArgumentException("Received message of unknown type " + request);
    }

    private static Response handleAutorizationRequest(AuthorizationRequest request) throws SQLException {
        String login = request.getLogin();
        String password = request.getPassword();
        User user = userDao.findByLogin(login);
        if(Server.connectedClients.containsKey(login)){
            return FactoryResponse.getDoubleRegisteredResponse();
        }
        if (user != null && user.getPassword().equals(password)) {
            LOGGER.info("Client " + login + " autorizated");
            return FactoryResponse.getAuthorizedResponse();
        } else {
            return FactoryResponse.getNotAutorizedResponse();
        }
    }

    private static Response handleRegistrationRequest(RegistrationRequest request) throws SQLException {
        String login = request.getLogin();
        String password = request.getPassword();
        if (userDao.findByLogin(login) != null) {
            return FactoryResponse.getNotRegisteredResponse();
        } else {
            User user = new User(login, password, new Date());
            userDao.insertUser(user);
            return FactoryResponse.getRegisteredResponse();
        }
    }
}
