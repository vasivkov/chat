package com.vasivkov.chat.client.util;

import com.vasivkov.chat.common.AuthorizationRequest;
import com.vasivkov.chat.common.ClientLeftRequest;
import com.vasivkov.chat.common.RegistrationRequest;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class ConsoleUtil {
    private static final Logger LOGGER = Logger.getLogger(ConsoleUtil.class.getName());

    public static AuthorizationRequest dataForAuthorization(BufferedReader br) {
        try {
            System.out.print("Enter your login: ");
            String login = br.readLine();
            System.out.print("Enter your password: ");
            String password = br.readLine();
            return new AuthorizationRequest(login, EncryptionUtil.generateHash(password));
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
            return new RegistrationRequest(login, EncryptionUtil.generateHash(password));
        } catch (IOException e) {
            LOGGER.error("Failed to read data from console to autorization", e);
            throw new RuntimeException(e);
        }
    }

    public static ClientLeftRequest dataForCloseingConnection() {
        System.out.println("BYE!");
        return new ClientLeftRequest();
    }
}
