package com.vasivkov.chat.client;

import com.vasivkov.chat.common.Message;
import org.apache.log4j.Logger;

import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadForReadingMessages implements Runnable {
    private ObjectInputStream ois;
    private static final Logger LOGGER = Logger.getLogger(ThreadForReadingMessages.class.getName());

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
                LOGGER.error("Failed to get data from server", e);
                return;
            }
            if (object instanceof Message) {
                Message message = (Message) object;
                String text = message.getText();
                String login = message.getAuthor();
                Date now = message.getDate();
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String s = formatter.format(now);
               System.out.println("   " + login + "(" + s + ")" + " >> " + text);
            }
        }
    }
}
