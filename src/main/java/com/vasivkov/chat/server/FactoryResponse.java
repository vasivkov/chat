package com.vasivkov.chat.server;

import com.vasivkov.chat.common.Response;

/**
 * Created by vasya on 18/07/17.
 */
public class FactoryResponse {
    public static Response getServerErrorResponse(){

        return new Response(true, "Could not connect to the server. Please try again later.");
    }


    public static Response getAuthorizedResponse(){
        return new Response(true, "You are authorized!");
    }

    public static Response getNotAutorizedResponse(){

        return new Response(false, "Incorrect login or password");
    }

    public static Response getRegisteredResponse(){

        return new Response(true, "You are registered!");
    }

    public static Response getNotRegisteredResponse(){

        return new Response(false, "Such login already exist");
    }

}


