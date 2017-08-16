package com.vasivkov.chat.server.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ChatAbstractDao {
    private static final Logger LOGGER = Logger.getLogger(ChatAbstractDao.class.getName());
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/chatUsers";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    Connection getConnection() {

        LOGGER.info("Try to connect to MySQL DB");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Failed to find JDBC driver");
            throw new RuntimeException();
        }

        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOGGER.info("Connecting to DB Success!");
        } catch (SQLException e) {
            LOGGER.error("Connection Failed!", e);
            throw new RuntimeException();
        }
        return connection;
    }
    void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Failed to close connection to database");
                throw new RuntimeException(e);
            }
        }
    }

    void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
