package com.vasivkov.chat.client;

import com.vasivkov.chat.common.ClosedConnectionRequest;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageTransportUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class ClientToServerMessageProcessor implements Runnable {
    public static final Logger LOGGER = Logger.getLogger(ClientToServerMessageProcessor.class.getName());
    private ObjectOutputStream oos;
    private BufferedReader br;

    public ClientToServerMessageProcessor(ObjectOutputStream oos, BufferedReader br) {
        this.oos = oos;
        this.br = br;
    }

    @Override
    public void run() {
        System.out.println("Please, write your message");
        while (true) {
            try {
                String text = br.readLine();
                ClientCommands command = ClientCommands.of(text);
                if (command == ClientCommands.QUIT) {
                    MessageTransportUtil.sendMessageNoGuarantee(new ClosedConnectionRequest(), oos);
                    return;
                }
                Message message = new Message(text, new Date());
                if (!"".equals(text)) {
                    MessageTransportUtil.sendMessageNoGuarantee(message, oos);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to sent message", e);
            }
        }
    }
}
