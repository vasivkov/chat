package com.vasivkov.chat.server;

/**
 * Created by vasya on 18/06/17.
 */

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


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        mapOfClient = new HashMap<>();
        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Users", "root", "");

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        ServerSocket serverSocket = new ServerSocket(2025);
        server.waitConnection(serverSocket);



    }


    private void waitConnection(ServerSocket serverSocket) {
        try {
            while (true) {
                System.out.println("Waiting connection...");
                Socket socket = serverSocket.accept();
                System.out.println("The client joined...");
                Thread thread = new Thread(new ServerConnection(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
