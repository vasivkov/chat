package com.vasivkov.chat.common;

/**
 * Created by vasya on 19/06/17.
 */
public class Message extends Request {
    private String text;
    private String login;

    public Message(String text, String login) {
        this.text = text;
        this.login = login;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getText() {
        return text;
    }


}
