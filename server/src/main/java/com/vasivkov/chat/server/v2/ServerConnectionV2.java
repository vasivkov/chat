package com.vasivkov.chat.server.v2;

import com.vasivkov.chat.common.Request;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ServerConnectionV2 implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ServerConnectionV2.class.getName());
    private  int id;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private BlockingQueue<Request> requests;
    private boolean isAuthorized;

    private ServerConnectionV2(Socket socket, BlockingQueue<Request> requests, int id) {
        this.requests = requests;
        this.isAuthorized = false;
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
        System.out.println("id-field in serverConnectionV2 = " + this.id);
        try {
            while (!isAuthorized) {
                Object object = ois.readObject();
                Request request = (Request) object;
                request.setId(this.id);
                System.out.println(" - request.getId = " + request.getId());
                requests.add(request);

            }
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Failed to read data from client");
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

}

