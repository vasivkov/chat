package com.vasivkov.chat.client;

import com.vasivkov.chat.common.ClientLeftRequest;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageRequest;
import com.vasivkov.chat.common.MessageTransportUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientToServerMessageProcessor implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(ClientToServerMessageProcessor.class.getName());
    private ObjectOutputStream oos;
    private BufferedReader br;
    private Socket socket;

    ClientToServerMessageProcessor(ObjectOutputStream oos, BufferedReader br, Socket socket) {
        this.socket = socket;
        this.oos = oos;
        this.br = br;
    }

    @Override
    public void run() {
        System.out.println("Please, write your message");
        boolean isFinished = false;
        while (!isFinished) {
            try {
                String text = br.readLine();
                if (text.toUpperCase().equals("Q")) {
                    MessageTransportUtil.sendMessageWithRepeat(new ClientLeftRequest(Client.getLogin()), oos, 5);
                    socket.close();
                    LOGGER.info(Client.getLogin() + " : exit from chat.");
                    isFinished = true;
                } else {
                    MessageRequest messageRequest = new MessageRequest(new Message(Client.getLogin(), text));
                    if (!"".equals(text)) {
                        MessageTransportUtil.sendMessageNoGuarantee(messageRequest, oos);
                    }
                }
            } catch (IOException e) {
                LOGGER.error(Client.getLogin() + ": Failed to sent message", e);
            }
        }
    }

}
