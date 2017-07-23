package com.vasivkov.chat.common;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String text;
    private String author;
    private Date date;


    public Message(String text, String author, Date date) {
        this.text = text;
        this.author = author;
        this.date = date;
    }

    public Message(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
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

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return author != null ? author.equals(message.author) : message.author == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }
}
