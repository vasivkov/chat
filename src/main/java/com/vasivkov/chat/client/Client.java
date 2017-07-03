package com.vasivkov.chat.client;

import com.vasivkov.chat.common.AutorizationRequest;

import java.io.*;
import java.net.*;

public class Client implements Serializable, Listener {



    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localHost", 2025);
        }catch (IOException e){
            e.printStackTrace();
        }
        ClientConnection clientConnection = new ClientConnection(socket);
        clientConnection.connect();
    }



    @Override
    public void update(String mess) {

    }
}

