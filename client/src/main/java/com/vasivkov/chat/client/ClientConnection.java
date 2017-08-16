package com.vasivkov.chat.client;

import com.vasivkov.chat.client.util.ConsoleUtil;
import com.vasivkov.chat.common.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    private static final Logger LOGGER = Logger.getLogger(ClientConnection.class.getName());
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private BufferedReader br;

    ClientConnection(Socket socket) {
        this.socket = socket;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            LOGGER.fatal("Failed to create streams for reading and writing messages from server", e);
            throw new RuntimeException(e);
        }
    }

    void connect() {
        boolean finished = false;
        while (!finished) {
            System.out.println("For registration: R, for authorization: A, for exit: Q");
            String choice = "";
            try {
                choice = br.readLine();
            } catch (IOException e) {
                LOGGER.error("Failed to read from console", e);
            }

            Request rq;
            ClientCommands command = ClientCommands.of(choice.toUpperCase());
            switch (command) {
                case AUTHORIZATION:
                    rq = ConsoleUtil.dataForAuthorization(br);
                    break;
                case REGISTRATION:
                    rq = ConsoleUtil.dataForRegistration(br);
                    break;
                case QUIT:
                    rq = ConsoleUtil.dataForCloseingConnection();
                    break;
                default:
                    System.out.println("invalid command " + choice);
                    continue;
            }

            try {
                MessageTransportUtil.sendMessageWithRepeat(rq, oos, 5);
                if (rq instanceof ClientLeftRequest) {
                    socket.close();
                    LOGGER.info("Socket has closed.");
                    return;
                }
                Object object = ois.readObject();
                if (object instanceof GeneralResponse) {
                    GeneralResponse response = (GeneralResponse) object;
                    if (response.isOutcome()) {
                        System.out.println(response.getError());
                        Thread writingThread = new Thread(new ClientToServerMessageProcessor(oos, br, socket));
                        Thread readingThread = new Thread(new ServerToClientMessageProcessor(ois));
                        writingThread.start();
                        readingThread.start();
                        finished = true;
                        LOGGER.info("Connecting to chat success!");
                    } else {
                        System.out.println(response.getError());
                        LOGGER.info("Failed to connect to chat. " + response.getError());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Failed to get response from server", e);
            }
        }
    }
}
