package com.vasivkov.chat.common;

/**
 * Created by vasya on 22/06/17.
 */
public class ResultResponse extends AbstractMessage {
    boolean result;

    public ResultResponse(boolean result){
        this.result = result;
    }
}
