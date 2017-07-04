package com.vasivkov.chat.server;

import com.vasivkov.chat.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by vasya on 24/06/17.
 */
public class ServerConnection implements Runnable {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ServerConnection(Socket socket) {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("потоки созданы");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        System.out.println("Заходим в Run");
        boolean tryClose = false;
        while (!tryClose) {
            try {

                Object object = ois.readObject();
                if (object instanceof Request) {
                    Request request = (Request) object;

                    Response response = MessageHandler.handlerOfRequest(request);
                    oos.writeObject(response);
                    oos.flush();
                    tryClose = true;
                }

//                if (object instanceof Message){
//                    // может ли пользователь отправить что-то кроме сообщения после регистрации???
//
//                }
//                AutorizationResponse checking;
//                if (object instanceof RegistrationRequest) {
//                    RegistrationRequest rr = (RegistrationRequest) object;
//
//                    if (ActiveUsers.users.containsKey(rr.getLogin())) {
//                        checking = new AutorizationResponse(false);
//                    } else {
//                        ActiveUsers.users.put(rr.getLogin(), rr.getPassword());
//                        checking = new AutorizationResponse(true);
//                    }
//                    oos.writeObject(checking);
//                    oos.flush();
//
//                }
//
//                if(object instanceof AutorizationRequest){
//                    AutorizationRequest ar = (AutorizationRequest) object;
//                    if(ActiveUsers.users.containsKey(ar.getLogin()) && ActiveUsers.users.get(ar.getLogin()).equals(ar.getPassword())){ // если в карте есть данное имя и пароли совпадают
//                        checking = new AutorizationResponse(true);
//                    }else{
//                        checking = new AutorizationResponse(false);
//                    }
//                    oos.writeObject(checking);
//                    oos.flush();
//                }
            } catch (Exception e) {
                e.printStackTrace();

            }


        }
    }
}
