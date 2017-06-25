package com.vasivkov.chat.common;

/**
 * Created by vasya on 24/06/17.
 */
public class RegistrationRequest extends AbstractMessage {
    String login;
    String password;

    public RegistrationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
