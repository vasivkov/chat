package com.vasivkov.chat.common;

import java.io.Serializable;

/**
 * Created by vasya on 01/07/17.
 */
public class Response implements Serializable {
    private boolean result;

    public Response(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }


}
