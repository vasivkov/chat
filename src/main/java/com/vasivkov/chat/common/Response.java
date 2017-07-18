package com.vasivkov.chat.common;

import java.io.Serializable;

/**
 * Created by vasya on 01/07/17.
 */
public class Response implements Serializable {
    private boolean result;
    private String responseMessage;

    public Response(boolean result) {
        this.result = result;
    }

    public Response(boolean result, String responseMessage) {
        this.result = result;
        this.responseMessage = responseMessage;
    }

    public boolean isResult() {
        return result;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    @Override
    public String toString() {
        return "Response{" +
                "result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        return result == response.result;
    }

    @Override
    public int hashCode() {
        return (result ? 1 : 0);
    }
}
