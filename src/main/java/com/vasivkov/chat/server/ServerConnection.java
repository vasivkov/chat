package com.vasivkov.chat.server;

import com.vasivkov.chat.client.ClientConnection;
import com.vasivkov.chat.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by vasya on 24/06/17.
 */
public class ServerConnection implements Runnable {
    private boolean clientExit;
    private String login;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    public String getLogin() {
        return login;
    }

    public ObjectInputStream getOis() {
        return ois;
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

    public boolean isClientExit() {
        return clientExit;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void run() {
        while (!clientExit) {
            try {
                Object object = ois.readObject();

                if (object instanceof Message && login!= null) {
                    Message message = (Message) object;
                    message.setLogin(login);
                    for (int i = 0; i < Server.listOfClient.size(); i++) {
                        if (!login.equals(Server.listOfClient.get(i).getLogin())) {
                            Server.listOfClient.get(i).getOos().writeObject(message);
                            Server.listOfClient.get(i).getOos().flush();
                        }
                    }
                }
                if ((object instanceof Request) && (!(object instanceof Message))) {
                    Request request = (Request) object;
                    Response response = MessageHandler.handlerOfRequest(request, this);
                    oos.writeObject(response);
                    oos.flush();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
