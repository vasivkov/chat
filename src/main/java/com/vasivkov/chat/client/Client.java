package com.vasivkov.chat.client;

import java.io.*;
import java.net.*;

public class Client implements Serializable {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localHost", 2025);

        ClientConnection clientConnection = new ClientConnection(socket);
        clientConnection.connect();

    }



}
