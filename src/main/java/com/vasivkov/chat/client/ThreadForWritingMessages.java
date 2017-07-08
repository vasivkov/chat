package com.vasivkov.chat.client;

import com.vasivkov.chat.common.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by vasya on 05/07/17.
 */
public class ThreadForWritingMessages implements Runnable{
    private ObjectOutputStream oos;
    private BufferedReader br;

    public ThreadForWritingMessages(ObjectOutputStream oos, BufferedReader br) {
        this.oos = oos;
        this.br = br;
    }

    @Override
    public void run() {
        System.out.println("Please, write your message");
        try {
            String text = br.readLine();
            Message message = new Message(text, "");
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
