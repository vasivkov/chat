package com.vasivkov.chat.common;

/**
 * Created by vasya on 19/06/17.
 */
public class AutorizationRequest extends AbstractMessage {
    private String login;
    private String password;
    private String city;
    private String mail;


    public AutorizationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public String getMail() {
        return mail;
    }
}
