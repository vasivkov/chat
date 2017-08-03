package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.common.*;


public class StrategyFactory {

    public static Strategy getStrategy(Request request) {
        if (request instanceof AuthorizationRequest) {
            return new AuthorizationStrategy();
        } else if (request instanceof RegistrationRequest) {
            return new RegistrationStrategy();
        }else if (request instanceof MessageRequest){
            return new MessageStrategy();
        }else if(request instanceof ClientLeftRequest){
            return  new ClientLeftStrategy();
        }
        return null;
    }

}
