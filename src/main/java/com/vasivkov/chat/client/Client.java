package com.vasivkov.chat.client;

import com.vasivkov.chat.common.AutorizationRequest;

import java.io.*;
import java.net.*;
public class Client implements Serializable, Listener{
    Socket socket;

    public Client(String host, int port ) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }


    }


    public AutorizationRequest registration() throws IOException {
       String nickName = null;
       String password = null;

        try( BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Enter your nickName");
            nickName = br.readLine();
            System.out.println("Enter your password");
            password = br.readLine();

        }
        return  new AutorizationRequest(nickName, password);
    }

    @Override
    public void update(String mess) {

    }
}

