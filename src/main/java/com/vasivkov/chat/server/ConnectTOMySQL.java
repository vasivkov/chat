package com.vasivkov.chat.server;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectTOMySQL {
    public static final Logger LOGGER =Logger.getLogger(ConnectTOMySQL.class.getName());

    protected static Connection connectToDB(String DBName, String user, String password){
        LOGGER.info("Try to connect to MySQL DB");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Failed to find JDBC driver");
            throw new RuntimeException();
        }
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBName, user, password);
            LOGGER.info("Connecting to DB Success!" );
        } catch (SQLException e) {
            LOGGER.error("Connection Failed!", e);
            throw new RuntimeException();
        }
        return connection;
    }

}
