package com.vasivkov.chat.server.v2.vo;

public class MessageRequest extends Request {
    private Message message;

    public MessageRequest(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageRequest that = (MessageRequest) o;

        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "message=" + message +
                '}';
    }


    @Override
    public String getSender() {
        return message.getAuthor();
    }
}
