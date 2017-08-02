package com.vasivkov.chat.server.v2;

import com.vasivkov.chat.server.v2.vo.Request;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ServerConnectionV2 implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ServerConnectionV2.class.getName());
    private Socket socket;
    private int ID;
    private boolean isConnected = false;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private BlockingQueue<Request> requests;
    private boolean isActive;

    public ServerConnectionV2(Socket socket, BlockingQueue<Request> requests, int ID) {
        this.socket = socket;
        this.requests = requests;
        this.ID = ID;
        this.isActive = true;


        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create i/o sockets");
        }
    }


    @Override
    public void run() {
        //TODO read from socket, check that instance of Request, add to requests queue
        try {
            while (!isActive) {
                Object object = ois.readObject();
                Request request = (Request) object;
                request.setID(ID);


            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private ObjectInputStream getInputStream(Socket socket) {
        try {
            return new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.fatal("Failed to create i/o streams", e);
            throw new RuntimeException(e);
        }
    }

    private Object readNextObject(ObjectInputStream ois) {
        Object obj = null;
        try {
            obj = ois.readObject();
        } catch (Exception e) {
            LOGGER.error("Failed to read object");
        }
        return obj;
    }


    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }
}

