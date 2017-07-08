package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;

import java.io.IOException;


public class MessageHandler {
    public static Response handlerOfRequest(Request request, ServerConnection serverConnection) {
        String login;
        String password;
        boolean result = true; // TODO
        if (request instanceof AutorizationRequest) {
            login = ((AutorizationRequest) request).getLogin();
            password = ((AutorizationRequest) request).getPassword();
            if (ActiveUsers.users.containsKey(login) && ActiveUsers.users.get(login).equals(password)) {
                Server.listOfClient.add(serverConnection);
                serverConnection.setLogin(login);
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
                Server.listOfClient.add(serverConnection);
                serverConnection.setLogin(login);
            }
        }

        return new Response(result);

    }
}
