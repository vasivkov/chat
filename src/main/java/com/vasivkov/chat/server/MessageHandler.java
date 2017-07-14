package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;
import org.apache.log4j.Logger;


public class MessageHandler {
    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());
    public static Response handlerOfRequest(Request request, ServerConnection serverConnection) {
        String login;
        String password;
        boolean result = true;
        if (request instanceof AutorizationRequest) {
            login = ((AutorizationRequest) request).getLogin();
            password = ((AutorizationRequest) request).getPassword();
            if (ActiveUsers.users.containsKey(login) && ActiveUsers.users.get(login).equals(password)) {
                Server.mapOfClient.put(login, serverConnection);
                serverConnection.setLogin(login);
                LOGGER.info("Client " + login + " autorizated");
            } else {
                result = false;
            }
        }

        if (request instanceof RegistrationRequest) {
            login = ((RegistrationRequest) request).getLogin();
            password = ((RegistrationRequest) request).getPassword();
            if (ActiveUsers.users.containsKey(login)) {
                result = false;
            } else {
                ActiveUsers.users.put(login, password);
                Server.mapOfClient.put(login, serverConnection);
                serverConnection.setLogin(login);
                LOGGER.info("Client " + login + " registrated");
            }
        }

        if (request instanceof ClosedConnectionRequest) {
            result = true;
        }

        return new Response(result);

    }
}
