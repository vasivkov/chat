package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

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
                    message.setAuthor(login);
                    if (!message.getText().equals("")) {
                        sendToAllClients(message);
                    }
                } else if (object instanceof ClosedConnectionRequest) {
                    Message message = new Message(login + " LEFT THE CHAT", "  ");
                    sendToAllClients(message);
                    Server.mapOfClient.remove(login);
                    System.out.println(Server.mapOfClient);
                    isClientExit = true;
                } else if (object instanceof Request) {
                    Request request = (Request) object;
                    Response response = MessageHandler.handlerOfRequest(request);
                    if (response.isResult()) {
                        login = request.getLogin();
                        Server.mapOfClient.put(login, this);
                    }
                    MessageTransportUtil.sendMessageWithRepeat(response, oos, 5);
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

    private void sendToAllClients(Object o) {
        for (Map.Entry<String, ServerConnection> entry : Server.mapOfClient.entrySet()) {
            if (!login.equals(entry.getKey())) {
                MessageTransportUtil.sendMessageNoGuarantee(o, entry.getValue().getOos());
            }
        }
    }
}

