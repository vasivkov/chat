package com.vasivkov.chat.client;

import com.vasivkov.chat.common.AutorizationRequest;

import java.io.*;
import java.net.Socket;

/**
 * Created by vasya on 27/06/17.
 */
public class ClientConnection implements Runnable {

    ObjectInputStream ois;
    ObjectOutputStream oos;
    BufferedReader br;


    public ClientConnection(Socket socket) {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(System.in));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void run() {


    }
}
