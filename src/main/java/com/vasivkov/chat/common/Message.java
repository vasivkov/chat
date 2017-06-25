package com.vasivkov.chat.common;

/**
 * Created by vasya on 19/06/17.
 */
public class Message extends AbstractMessage {
    private String text;
    private String login;

    public Message(String text, String login) {
        this.text = text;
        this.login = login;
    }

    public String getText() {
        return text;
    }

    public String getLogin() {
        return login;
    }
}
