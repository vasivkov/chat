package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Created by vasya on 24/06/17.
 */
public class ServerConnection implements Runnable {
    private String login;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private static final Logger LOGGER = Logger.getLogger(ServerConnection.class.getName());

    public void setLogin(String login) {
        this.login = login;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }


    public ServerConnection(Socket socket) {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.fatal("Failed to create streams", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        boolean isClientExit = false;
        try {
            while (!isClientExit) {
                Object object = ois.readObject();
                LOGGER.info("Recieved message " + object);
                if (object instanceof Message) {
                    Message message = (Message) object;
                    message.setLogin(login);
                    if (!message.getText().equals("")) {
                        sendToAllClients(message);
                    }
                }

                if ((object instanceof Request)) {
                    Request request = (Request) object;
                    Response response = MessageHandler.handlerOfRequest(request, this);
                    sendMessageWithRepeat(response, oos, 5);
                }
                if (object instanceof ClosedConnectionRequest) {
                    Message message = new Message(login + " LEFT THE CHAT", "  ");
                    sendToAllClients(message);
                    Server.mapOfClient.remove(login);
                    isClientExit = true;
                }
            }

        } catch (Exception e) {
            LOGGER.error("Failed to read from socket", e);

        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(oos);
            LOGGER.info("Streams are closed. Client " + login + " is disconnected");
        }
    }

    public void sendMessageNoGuarantee(Object o, ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(o);
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("Failed to send  message of " + o.getClass().getSimpleName(), e);
        }
    }

    public void sendMessageWithRepeat(Object o, ObjectOutputStream outputStream, int times) {
        if (times < 1) {
            throw new IllegalArgumentException();
        }
        int tries = 0;
        boolean delivered = false;

        while (tries < times && !delivered) {
            try {
                outputStream.writeObject(o);
                outputStream.flush();
                delivered = true;
            } catch (IOException e) {
                tries++;
                if(tries == times) {
                    LOGGER.error("Failed to send message of " + o.getClass().getSimpleName());
                }
                e.printStackTrace();
            }
        }
    }


    private void sendToAllClients(Object o) {
        for (Map.Entry<String, ServerConnection> entry : Server.mapOfClient.entrySet()) {
            if (!login.equals(entry.getKey())) {
                sendMessageNoGuarantee(o, entry.getValue().getOos());
            }
        }
    }

}

