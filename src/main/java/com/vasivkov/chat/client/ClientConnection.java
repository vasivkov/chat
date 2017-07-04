package com.vasivkov.chat.client;

import com.vasivkov.chat.common.*;

import java.io.*;
import java.net.Socket;

public class ClientConnection {

    ObjectInputStream ois;
    ObjectOutputStream oos;
    BufferedReader br;

    public ClientConnection(Socket socket) {
        try {

            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
           br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("потоки клиента созданы");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void connect() {

        boolean finished = false;
            while (!finished) {
                System.out.println("For registration: R, For autariation A, For exit !E!");
                String choice = "";
                try {
                    choice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Request rq = null; // потом сделать запрос выхода
                if (choice.equalsIgnoreCase("R")) {
                    rq = ConsoleUtil.dataForRegistration(br);
                }
                if (choice.equalsIgnoreCase("A")) {
                    rq = ConsoleUtil.dataForAutorization(br);
                }
                try {
                    oos.writeObject(rq);
                    oos.flush();
                    System.out.println("Данные отправлены");
                    Object object = ois.readObject();
                    if (object instanceof Response) {
                        Response response = (Response) object;
                        if (response.isResult()) {
                            finished = true;
                            System.out.println("Success");
                        } else {
                            System.out.println("Problems...");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

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


//}
