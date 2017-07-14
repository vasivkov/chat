package com.vasivkov.chat.client;

import com.vasivkov.chat.common.Message;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by vasya on 05/07/17.
 */
public class ThreadForReadingMessages implements Runnable {
    private ObjectInputStream ois;
public static final Logger LOGGER =Logger.getLogger(ThreadForReadingMessages.class.getName());
    public ThreadForReadingMessages(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public void run() {
        while (true) {
            Object object;
            try {
                object = ois.readObject();
            } catch (Exception e) {
                System.out.println("Socket closed");
                LOGGER.error("Failed to get data from server", e);
                return;
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
