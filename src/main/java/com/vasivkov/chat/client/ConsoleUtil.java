package com.vasivkov.chat.client;

import com.vasivkov.chat.common.AutorizationRequest;
import com.vasivkov.chat.common.ClosedConnectionRequest;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.RegistrationRequest;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleUtil {
public static final Logger LOGGER = Logger.getLogger(ConsoleUtil.class.getName());

    public static AutorizationRequest dataForAutorization(BufferedReader br) {

        try {
            System.out.print("Enter your login: ");
            String login = br.readLine();
            System.out.print("Enter your password: ");
            String password = br.readLine();
            return new AutorizationRequest(login, password);
        } catch (IOException e) {
            LOGGER.error("Failed to read data from console to autorization", e);
            throw new RuntimeException(e);
        }

    }


    public static RegistrationRequest dataForRegistration(BufferedReader br) {
        try {
            System.out.print("Think up your login: ");
            String login = br.readLine();
            System.out.print("Think up the password: ");
            String password = br.readLine();
            return new RegistrationRequest(login, password);
        } catch (IOException e) {
            LOGGER.error("Failed to read data from console to autorization", e);
            throw new RuntimeException(e);
        }
    }

    public static ClosedConnectionRequest dataForCloseingConnection (){
        System.out.println("BYE!");
        return new ClosedConnectionRequest();
    }

}
