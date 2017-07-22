package com.vasivkov.chat.client;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 2025);
        ClientConnection clientConnection = new ClientConnection(socket);
        clientConnection.connect();
    }
}







