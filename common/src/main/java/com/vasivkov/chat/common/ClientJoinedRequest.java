package com.vasivkov.chat.common;

public class ClientJoinedRequest extends Request {

    private String clientLogin;

    public ClientJoinedRequest(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientJoinedRequest that = (ClientJoinedRequest) o;

        return clientLogin != null ? clientLogin.equals(that.clientLogin) : that.clientLogin == null;
    }

    @Override
    public int hashCode() {
        return clientLogin != null ? clientLogin.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ClientJoinedRequest{" +
                "clientLogin='" + clientLogin + '\'' +
                '}';
    }

    @Override
    public String getSender() {
        return clientLogin;
    }
}
