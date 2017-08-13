package com.vasivkov.chat.server;

import com.vasivkov.chat.common.Request;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ServerConnection implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ServerConnection.class.getName());
    private  int id;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private BlockingQueue<Request> requests;
    private boolean isAuthorized;
    private boolean isConnected;

    public ServerConnection() {
    }

    public ServerConnection(Socket socket, BlockingQueue<Request> requests, int id) {
        this.requests = requests;
        this.isAuthorized = false;
        this.isConnected = true;
        this.id = id;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create i/o sockets");
        }
    }

    @Override
    public void run() {
        try {
            while (isConnected) {
                Object object = ois.readObject();
                if(object instanceof Request) {
                    Request request = (Request) object;
                    request.setId(this.id);
                    requests.add(request);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Failed to read data from client", e);
        }
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

}

