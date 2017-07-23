package com.vasivkov.chat.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class UserDao extends ChatAbstractDao {
    private static final String FIND_BY_LOGIN = "SELECT login, password FROM users WHERE login=?";
    private static final String INSERT_USER = "INSERT INTO users (login, password, date_of_registration) VALUES(?, ?, ?)";
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    User findByLogin(String login) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                return user;
            } else {
                return null;
            }
        } finally {
            close(connection);
            close(preparedStatement);
        }
    }

    void insertUser(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, SDF.format(user.getDateOfRegistration()));
            preparedStatement.executeUpdate();

        } finally {
            close(connection);
            close(preparedStatement);
        }
    }
}
