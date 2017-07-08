package com.vasivkov.chat.client;

import com.vasivkov.chat.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by vasya on 05/07/17.
 */
public class ThreadForReadingMessages implements Runnable {
    private ObjectInputStream ois;

    public ThreadForReadingMessages(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public void run() {
        while (true) {
            Object object = null;
            try {
                object = ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();

            }
            if (object instanceof Message) {
                Message message = (Message) object;
                String text = message.getText();
                String login = message.getLogin();
                System.out.println("   " + login + " >> "+ text);
            }
        }
    }
}
