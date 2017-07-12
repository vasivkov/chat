package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;
import org.apache.commons.io.IOUtils;

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
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        boolean isClientExit = false;
        try {
            while (!isClientExit) {
                Object object = ois.readObject();
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
            e.printStackTrace();

        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(oos);
        }
    }

    public void sendMessageNoGuarantee(Object o, ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(o);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
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

