package com.vasivkov.chat.server;

import com.vasivkov.chat.client.Listener;

/**
 * Created by vasya on 20/06/17.
 */
public interface Subject {
    void addClient(Listener listener);
    void removeClient(Listener listener);
    void notifyClients();
}
