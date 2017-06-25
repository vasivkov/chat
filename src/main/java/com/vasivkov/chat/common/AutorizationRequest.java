package com.vasivkov.chat.common;

/**
 * Created by vasya on 19/06/17.
 */
public class AutorizationRequest extends AbstractMessage {
    private String login;
    private String password;


    public AutorizationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
