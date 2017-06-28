package com.vasivkov.chat.client;

import com.vasivkov.chat.common.AutorizationRequest;
import com.vasivkov.chat.common.RegistrationRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by vasya on 28/06/17.
 */
public class ClientData {
    private String login;
    private String password;
    private String city;
    private String mail;


    public AutorizationRequest dataForAutorization(BufferedReader br) {
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter your login: ");
            login = br.readLine();
            System.out.println("Enter your password: ");
            password = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AutorizationRequest(login, password);
    }


    public RegistrationRequest dataForRegistration(BufferedReader br) {
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Think up your login: ");
            login = br.readLine();
            System.out.println("Think up the password: ");
            password = br.readLine();
            System.out.println("Where do you live (our city): ");
            city = br.readLine();
            System.out.println("Your mail: ");
            mail = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RegistrationRequest(login, password, city, mail);
    }
}
