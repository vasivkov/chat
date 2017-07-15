package com.vasivkov.chat.client;

import com.vasivkov.chat.common.ClosedConnectionRequest;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.SendMessage;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class ThreadForWritingMessages implements Runnable{
    public static final Logger LOGGER = Logger.getLogger(ThreadForWritingMessages.class.getName());
    private ObjectOutputStream oos;
    private BufferedReader br;

    public ThreadForWritingMessages(ObjectOutputStream oos, BufferedReader br) {
        this.oos = oos;
        this.br = br;
    }

    @Override
    public void run() {
        System.out.println("Please, write your message");
        while (true) {
            String text = null;
            try {
                text = br.readLine();
                if(text.equalsIgnoreCase("E")){
                    SendMessage.sendMessageNoGuarantee(new ClosedConnectionRequest(), oos);
                    return;
                }
                Message message = new Message(text);
                if(!"".equals(text)) {
                    SendMessage.sendMessageNoGuarantee(message, oos);
                }
            } catch (IOException e) {
               LOGGER.error("Failed to sent message", e);
            }
        }
    }
}
