package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Date;


public class MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());

    public static Response handlerOfRequest(Request request, ServerConnection serverConnection) {
        String login;
        String password;
        boolean result = true;
        String responseMessage = "";
        if (request instanceof AutorizationRequest) {
            login = ((AutorizationRequest) request).getLogin();
            password = ((AutorizationRequest) request).getPassword();
            ChatDao chatDao = new ChatDao();
            try {
                User user = chatDao.findByLogin(login);
                if(user != null && user.getPassword().equals(password) ){
                    responseMessage = "You are authorized!";
                    LOGGER.info("Client " + login + " autorizated");
                }else {
                    result = false;
                    responseMessage = "Incorrect login or pasword";
                }
            }catch (SQLException e){
                result = false;
                responseMessage = "Could not connect to the server. Please try again later.";
            }

        }

        if (request instanceof RegistrationRequest) {
            login = ((RegistrationRequest) request).getLogin();
            password = ((RegistrationRequest) request).getPassword();
            ChatDao chatDao = new ChatDao();
            try {
                if (chatDao.findByLogin(login) != null) {
                    result = false;
                    responseMessage = "Such login already exist";
                } else {
                    User user = new User();
                    user.setDateOfRegistration(new Date());
                    user.setLogin(login);
                    user.setPassword(password);
                    chatDao.insertUser(user);
                    responseMessage = "You are registered!";
                }

            } catch (SQLException e) {
                result = false;
                responseMessage = "Could not connect to the server. Please try again later.";
            }


        }

        return new Response(result, responseMessage);

    }
}
