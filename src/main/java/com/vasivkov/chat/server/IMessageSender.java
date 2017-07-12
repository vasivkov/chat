package com.vasivkov.chat.server;

/**
 * Created by vasya on 11/07/17.
 */
@FunctionalInterface
public interface IMessageSender {
    void sendMessage(Object o);
}
