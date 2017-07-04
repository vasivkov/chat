package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;

/**
 * Created by vasya on 01/07/17.
 */
public class MessageHandler {
    public static Response handlerOfRequest(Request request) {
        String login ;
        String password;
        if (request instanceof AutorizationRequest) {
            login = ((AutorizationRequest) request).getLogin();
            password = ((AutorizationRequest) request).getPassword();
            if (ActiveUsers.users.containsKey(login) && ActiveUsers.users.get(login).equals(password)) {
                Server.listOfClient.add(login);
                return  new Response(true);
            } else {
                return new Response(false);
            }
        }
        if(request instanceof RegistrationRequest){
            login = ((RegistrationRequest) request).getLogin();
            password = ((RegistrationRequest) request).getPassword();
            if(ActiveUsers.users.containsKey(login)){
                return new Response(false);
            }else{
                ActiveUsers.users.put(login, password);
                System.out.println(ActiveUsers.users);
               Server.listOfClient.add(login);
                System.out.println(Server.listOfClient);
                return  new Response(true);
            }
        }else {
            throw  new RuntimeException(); // TODO
        }

    }




}
