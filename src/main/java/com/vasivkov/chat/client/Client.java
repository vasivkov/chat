package com.vasivkov.chat.client;

import com.vasivkov.chat.common.AutorizationRequest;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client implements Serializable, Listener {
    Socket socket;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void main(String[] args) {
        Client client = new Client("lockalhost", 2024);
        Socket socket = client.socket;
        ClientConnection clientConnection = new ClientConnection(socket);
        ClientData clientData = new ClientData();
        AutorizationRequest autorization = clientData.dataForAutorization(clientConnection.br);


    }


    @Override
    public void update(String mess) {

    }
}

