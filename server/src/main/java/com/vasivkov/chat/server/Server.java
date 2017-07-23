package com.vasivkov.chat.server;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    static Map<String, ServerConnection> mapOfClient;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) throws IOException {
        if (args.length != 1){
            System.out.println("Wrong input argument!");
            System.exit(-1);
        }
        Server server = new Server();
        mapOfClient = new HashMap<>();
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
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
