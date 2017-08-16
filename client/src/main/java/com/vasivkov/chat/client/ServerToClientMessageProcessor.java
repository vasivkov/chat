package com.vasivkov.chat.client;

import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageResponse;
import org.apache.log4j.Logger;

import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerToClientMessageProcessor implements Runnable {
    private ObjectInputStream ois;
    private static final Logger LOGGER = Logger.getLogger(ServerToClientMessageProcessor.class.getName());
    private static final String FORMAT = "hh:mm:ss";
    private static final DateFormat FORMATTER = new SimpleDateFormat(FORMAT);

    public ServerToClientMessageProcessor(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public void run() {
        while (true) {
            Object object;
            try {
                object = ois.readObject();
            } catch (Exception e) {
                LOGGER.error(Client.getLogin() + ": Failed to get data from server", e);
                return;
            }
            if (object instanceof MessageResponse) {
                MessageResponse messageResponse = (MessageResponse) object;
                Message message = messageResponse.getMessage();
                String text = message.getText();
                String login = message.getAuthor();
                Date now = message.getCreationDateTime();
                String s = FORMATTER.format(now);
                System.out.println("   " + login + "(" + s + ")" + " >> " + text);
            }
        }
    }
}
