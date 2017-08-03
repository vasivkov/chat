package com.vasivkov.chat.server;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//public class Server {
//    static Map<String, ServerConnection> connectedClients = new ConcurrentHashMap<>();
//    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
//
//    public static void main(String[] args) throws IOException {
//        if (args.length != 1) {
//            System.out.println("Wrong input argument!");
//            System.exit(-1);
//        }
//        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
//        LOGGER.info("Server started");
//        Server.waitConnection(serverSocket);
//    }
//
//    private static void waitConnection(ServerSocket serverSocket) {
//        Socket socket = null;
//        try {
//            while (true) {
//                socket = serverSocket.accept();
//                LOGGER.info("The client " + socket + " joined");
//                Thread thread = new Thread(new ServerConnection(socket));
//                thread.start();
//            }
//        } catch (IOException e) {
//            LOGGER.error("Failed to connect client", e);
//        } finally {
//            if(socket!= null) {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
