package com.vasivkov.chat.server.v2.vo;

/**
 * Created by eugene on 7/28/17.
 */
public class MessageResponse implements Response {
    private Message message;

    public MessageResponse(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageResponse that = (MessageResponse) o;

        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "message=" + message +
                '}';
    }
}
