package com.vasivkov.chat.client;

import com.vasivkov.chat.common.AutorizationRequest;
import com.vasivkov.chat.common.RegistrationRequest;
import com.vasivkov.chat.common.AutorizationResponse;

import java.io.*;
import java.net.Socket;

public class ClientConnection {

    ObjectInputStream ois;
    ObjectOutputStream oos;
    BufferedReader br;

    public ClientConnection(Socket socket) {
//        try {

//            ois = new ObjectInputStream(socket.getInputStream());
//            oos = new ObjectOutputStream(socket.getOutputStream());
           br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("потоки клиента созданы");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }



    public void connect() {
        System.out.println("For registration: R, For autariation A");

//        String choice = "";
//        try {
//            choice = br.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        boolean finished = false;
//        if (choice.equalsIgnoreCase("R")) {
//            RegistrationRequest registrationRequest = ConsoleUtil.dataForRegistration(br);
//            while (!finished) {
//                try {
//                    oos.writeObject(registrationRequest);
//                    oos.flush();
//                    Object object = ois.readObject();
//                    if (object instanceof AutorizationResponse) {
//                        AutorizationResponse autorizationResponse = (AutorizationResponse) object;
//                        if (autorizationResponse.isResult()) {
//                            finished = true;
//                        } else {
//                            System.out.println("Such name already exist");
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        if (choice.equals("A")) {
//            AutorizationRequest autorizationRequest = ConsoleUtil.dataForAutorization(br);
//            while (finished) {
//                try {
//                    oos.writeObject(autorizationRequest);
//                    oos.flush();
//                    Object object = ois.readObject();
//                    if (object instanceof AutorizationResponse) {
//                        AutorizationResponse autorizationResponse = (AutorizationResponse) object;
//                        if (autorizationResponse.isResult()) {
//                            finished = true;
//                        } else {
//                            System.out.println("Incorrect name or password");
//                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }


    }


}
