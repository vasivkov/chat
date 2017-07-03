package com.vasivkov.chat.common;

/**
 * Created by vasya on 22/06/17.
 */
public class AutorizationResponse extends Response {
    private boolean result;

    public AutorizationResponse(boolean result){
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
