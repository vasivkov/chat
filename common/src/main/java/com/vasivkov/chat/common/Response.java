package com.vasivkov.chat.common;

import java.io.Serializable;

public class Response implements Serializable {
    private boolean result;
    private String responseMessage;

    public Response(boolean isSuccessfull, String responseMessage) {
        this.result = isSuccessfull;
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
