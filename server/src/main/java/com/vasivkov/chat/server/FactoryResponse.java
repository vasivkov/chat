package com.vasivkov.chat.server;

import com.vasivkov.chat.common.Response;

public class FactoryResponse {

    static Response getServerErrorResponse() {
        return new Response(true, "Could not connect to the server. Please try again later.");
    }

    static Response getAuthorizedResponse() {
        return new Response(true, "You are authorized!");
    }

    static Response getNotAutorizedResponse() {
        return new Response(false, "Incorrect login or password");
    }

    static Response getRegisteredResponse() {
        return new Response(true, "You are registered!");
    }

    static Response getNotRegisteredResponse() {
        return new Response(false, "Such login already exist");
    }

    static Response getDoubleRegisteredResponse(){
        return  new Response(false, "Such user is already in the chat");
    }
}


