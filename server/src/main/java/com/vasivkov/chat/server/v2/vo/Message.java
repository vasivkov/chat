package com.vasivkov.chat.server.v2.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by eugene on 7/28/17.
 */
public class Message implements Serializable {
    private String author;
    private String text;
    private Date creationDateTime;

    public Message(String author, String text, Date creationDateTime) {
        this.author = author;
        this.text = text;
        this.creationDateTime = creationDateTime;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Date getCreationDateTime() {
        return creationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (author != null ? !author.equals(message.author) : message.author != null) return false;
        if (text != null ? !text.equals(message.text) : message.text != null) return false;
        return creationDateTime != null ? creationDateTime.equals(message.creationDateTime) : message.creationDateTime == null;
    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (creationDateTime != null ? creationDateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "author='" + author + '\'' +
                ", text='" + text + '\'' +
                ", creationDateTime=" + creationDateTime +
                '}';
    }
}