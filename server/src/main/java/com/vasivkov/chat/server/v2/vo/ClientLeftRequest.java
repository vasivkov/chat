package com.vasivkov.chat.server.v2.vo;

/**
 * Created by eugene on 7/28/17.
 */
public class ClientLeftRequest extends Request {
    private String clientLogin;

    public ClientLeftRequest(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientLeftRequest that = (ClientLeftRequest) o;

        return clientLogin != null ? clientLogin.equals(that.clientLogin) : that.clientLogin == null;
    }

    @Override
    public int hashCode() {
        return clientLogin != null ? clientLogin.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ClientLeftRequest{" +
                "clientLogin='" + clientLogin + '\'' +
                '}';
    }

    @Override
    public String getSender() {
        return clientLogin;
    }
}

