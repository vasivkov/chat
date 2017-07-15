package com.vasivkov.chat.server;


import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    static Map<String,ServerConnection> mapOfClient;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        mapOfClient = new HashMap<>();

        ServerSocket serverSocket = new ServerSocket(2025);
        LOGGER.info("Server started");
        server.waitConnection(serverSocket);
    }

    private void waitConnection(ServerSocket serverSocket) {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                LOGGER.info("The client " + socket + " joined");
                Thread thread = new Thread(new ServerConnection(socket));
                thread.start();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to connect client", e);
        }
    }
}
