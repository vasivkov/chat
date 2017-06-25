package com.vasivkov.chat.server;

/**
 * Created by vasya on 18/06/17.
 */

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        ServerSocket serverSocket = new ServerSocket(2024);
        server.waitConnection(serverSocket);
    }

    private void waitConnection(ServerSocket serverSocket) {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new Connection(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
