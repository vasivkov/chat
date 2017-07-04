package com.vasivkov.chat.common;

/**
 * Created by vasya on 24/06/17.
 */
public class RegistrationRequest extends Request {
   private String login;
   private String password;
//    String city;
//    String mail;

    public RegistrationRequest(String login, String password) {
        this.login = login;
        this.password = password;
//        this.city = city;
//        this.mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

//    public String getCity() {
//        return city;
//    }
//
//    public String getMail() {
//        return mail;
//    }

}
