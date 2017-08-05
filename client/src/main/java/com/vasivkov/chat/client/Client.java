package com.vasivkov.chat.client;

import java.io.IOException;
import java.net.Socket;
public class Client {

    public static void main(String[] args) throws IOException {
        if (args.length != 2){
            System.out.println("Wrong input arguments!");
            System.exit(-1);
        }
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        ClientConnection clientConnection = new ClientConnection(socket);
        clientConnection.connect();
    }
}








