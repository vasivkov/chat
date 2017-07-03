package com.vasivkov.chat.server;

import com.vasivkov.chat.common.AutorizationRequest;
import com.vasivkov.chat.common.AutorizationResponse;
import com.vasivkov.chat.common.Request;
import com.vasivkov.chat.common.Response;

/**
 * Created by vasya on 01/07/17.
 */
public class MessageHandler {
    public static Response handlerOfRequest(Request request) {
        if (request instanceof AutorizationRequest) {
            String login = ((AutorizationRequest) request).getLogin();
            String password = ((AutorizationRequest) request).getPassword();
            if (ActiveUsers.users.containsKey(login) && ActiveUsers.users.get(login).equals(password)) {
                Server.listOfClient.add(login);
                return new AutorizationResponse(true);
            } else {
                return new AutorizationResponse(false);
            }
        } else{
            return new Response(); //to do
        }
    }


}
