package com.vasivkov.chat.server;

import com.vasivkov.chat.client.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasya on 20/06/17.
 */
public class ChatServer implements Subject {
    private String message;
    List listOfClient;
    public ChatServer(){
        listOfClient = new ArrayList();
    }
    @Override
    public void addClient(Listener listener) {
        listOfClient.add(listener);
    }

    @Override
    public void removeClient(Listener listener) {
        int index = listOfClient.indexOf(listener);
        if(index >= 0){
            listOfClient.remove(index);
        }
    }

    @Override
    public void notifyClients() {
        for (int i = 0; i < listOfClient.size(); i++) {
            Listener listener = (Listener) listOfClient.get(i);
            listener.update(message);

        }
    }
}
