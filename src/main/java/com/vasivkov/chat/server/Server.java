package com.vasivkov.chat.server;

/**
 * Created by vasya on 18/06/17.
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    static   ArrayList<String> listOfClient;


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        listOfClient = new ArrayList<>();
        ServerSocket serverSocket = new ServerSocket(2025);
        server.waitConnection(serverSocket);
    }

    private void waitConnection(ServerSocket serverSocket) {
        try {
            while (true) {
                System.out.println("Waiting connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент присоединился...");
                Thread thread = new Thread(new ServerConnection(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
