package com.vasivkov.chat.server;

/**
 * Created by vasya on 16/07/17.
 */
public interface UserDAO {
    User findByLogin (String login);
    int insertUser (User user);
}
