package com.vasivkov.chat.client;

import com.vasivkov.chat.common.ClosedConnectionRequest;
import com.vasivkov.chat.common.Message;
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
            try {
                String text = br.readLine();
                if(text.equalsIgnoreCase("E")){
                    oos.writeObject(new ClosedConnectionRequest());
                    oos.flush();
                    return;
                }
                Message message = new Message(text);
                if(!"".equals(text)) {
                    oos.writeObject(message);
                    oos.flush();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
