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
            System.out.println("Streams of client created");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void connect() {

        boolean finished = false;
            while (!finished) {
                System.out.println("For registration: R, For autoriation: A, For exit: E");
                String choice = "";
                try {
                    choice = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Request rq = null; // потом сделать запрос выхода
                if(choice.equalsIgnoreCase("E")){
                    rq = ConsoleUtil.dataForCloseingConnection(br);
                }
                if (choice.equalsIgnoreCase("R")) {
                    rq = ConsoleUtil.dataForRegistration(br);
                }
                if (choice.equalsIgnoreCase("A")) {
                    rq = ConsoleUtil.dataForAutorization(br);
                }
                try {
                    oos.writeObject(rq);
                    oos.flush();
                    Object object = ois.readObject();
                    if (object instanceof Response) {
                        Response response = (Response) object;
                        if (response.isResult()) {
                            System.out.println("You are registered... or authorized...");
                            Thread writingThread = new Thread(new ThreadForWritingMessages(oos, br));
                            Thread readingThread = new Thread(new ThreadForReadingMessages(ois));
                            writingThread.start();
                            readingThread.start();
                            finished = true;
                        } else {
                            System.out.println("Problems...");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }
