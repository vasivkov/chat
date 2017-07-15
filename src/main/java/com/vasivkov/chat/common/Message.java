package com.vasivkov.chat.common;

/**
 * Created by vasya on 19/06/17.
 */
public class Message extends Request {
    private String text;
    private String author;

    public Message(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public Message(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public String toString(){
        return text;
    }
}
