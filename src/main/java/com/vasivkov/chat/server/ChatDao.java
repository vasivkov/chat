package com.vasivkov.chat.server;

import com.vasivkov.chat.common.Message;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChatDao {
    private static final Logger LOGGER = Logger.getLogger(ChatDao.class.getName());
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/chatUsers";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String FIND_BY_LOGIN = "SELECT login, password FROM users WHERE login=?";
    private static final String INSERT_USER = "INSERT INTO users (login, password, date_of_registration) VALUES(?, ?, ?)";
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String INSERT_MESSAGE = "INSERT INTO messages (text, message_datetime, author) VALUES (?, ?, ?)";
    private static final String GET_MESSAGE = "SELECT text, message_datetime, author FROM messages ORDER BY message_datetime DESC LIMIT 10";

    private Connection getConnection() {
        LOGGER.info("Try to connect to MySQL DB");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Failed to find JDBC driver");
            throw new RuntimeException();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.info("Connecting to DB Success!");
        } catch (SQLException e) {
            LOGGER.error("Connection Failed!", e);
            throw new RuntimeException();
        }
        return connection;
    }

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

    void insertMessage(Message message) throws SQLException {
        Date date = message.getDate();
        Timestamp timestamp = new Timestamp(date.getTime());
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_MESSAGE);
            preparedStatement.setString(1, message.getText());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.setString(3, message.getAuthor());
            preparedStatement.executeUpdate();
        } finally {
            close(connection);
            close(preparedStatement);
        }
    }



    List<Message> getLastTenMessages() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Message> messageList = new ArrayList<>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(GET_MESSAGE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message(resultSet.getString("text"),
                        resultSet.getString("author"),
                        resultSet.getTimestamp("message_datetime"));
                messageList.add(message);
            }
            Collections.reverse(messageList);
        } finally {
            close(connection);
            close(preparedStatement);
        }
        return messageList;
    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
