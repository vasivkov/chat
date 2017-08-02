package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.server.v2.vo.AuthorizationRequest;
import com.vasivkov.chat.server.v2.vo.RegistrationRequest;
import com.vasivkov.chat.server.v2.vo.Request;


public class StrategyFactory {

    public static Strategy getStrategy(Request request) {
        if (request instanceof AuthorizationRequest) {
            return new AuthorizationStrategy();
        } else if (request instanceof RegistrationRequest) {
            return new RegistrationStrategy();
        }
        //TODO create strategies for other types of requests
        return null;
    }

}
