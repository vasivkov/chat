package com.vasivkov.chat.server;

import com.vasivkov.chat.common.AutorizationRequest;
import com.vasivkov.chat.common.ResultResponse;
import com.vasivkov.chat.common.RegistrationRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by vasya on 24/06/17.
 */
public class Connection implements Runnable {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Connection(Socket socket) {
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object object = ois.readObject();
                if (object instanceof RegistrationRequest) {
                    RegistrationRequest rr = (RegistrationRequest) object;
                    ResultResponse checking;
                    if (ActiveUsers.users.containsKey(rr.getLogin())) {
                        checking = new ResultResponse(false);
                    } else {
                        ActiveUsers.users.put(rr.getLogin(), rr.getPassword());
                        checking = new ResultResponse(true);
                    }
                    oos.writeObject(checking);
                    oos.flush();

                }

                if(object instanceof AutorizationRequest){
                    AutorizationRequest ar = (AutorizationRequest) object;

                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
