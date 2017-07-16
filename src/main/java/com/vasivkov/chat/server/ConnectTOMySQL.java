package com.vasivkov.chat.server;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.log4j.Logger;

import java.sql.*;

public class ConnectTOMySQL implements UserDAO{
    public static final Logger LOGGER =Logger.getLogger(ConnectTOMySQL.class.getName());
    private static final String jdbcDriver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/Chat_DB";
    private static final String user = "root";
    private static final String password = "";

    private static final String FIND_BY_LOGIN = "SELECT * FROM users WHERE login=?";



    private Connection connectToDB(){
        LOGGER.info("Try to connect to MySQL DB");
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Failed to find JDBC driver");
            throw new RuntimeException();
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            LOGGER.info("Connecting to DB Success!");
        } catch (SQLException e) {
            LOGGER.error("Connection Failed!", e);
            throw new RuntimeException();
        }
        return connection;
    }

    @Override
    public User findByLogin(String login) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectToDB();
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE LOGIN=?");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setDateOfRegistration(resultSet.getDate("dateOfregistration"));
                user.setAge(resultSet.getInt("age"));
                User var = user;
                return var;
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }finally {
            close(connection);
            close(preparedStatement);
        }

        return null;
    }

    @Override
    public int insertUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = connectToDB();
            preparedStatement = connection.prepareStatement("INSERT INTO user (login, password, dateOfRegistration, age) VALUES(?, ?, ?, ?)");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, String.valueOf(user.getDateOfRegistration()));
            preparedStatement.setString(4, String.valueOf(user.getAge()));

            // TODO
        }catch(SQLException e){
            throw new RuntimeException(e);
        }finally {
            close(connection);
            close(preparedStatement);
        }

        return 0;
    }


    private static void close(Connection connection){
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    private static void close(Statement statement){
        if(statement != null){
            try{
                statement.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

}
