package com.vasivkov.chat.server;
import org.apache.log4j.Logger;
import java.sql.*;

public class ChatDao {
    public static final Logger LOGGER =Logger.getLogger(ChatDao.class.getName());
    private static final String jdbcDriver = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/Chat_DB";
    private static final String user = "root";
    private static final String password = "";
    private static final String FIND_BY_LOGIN = "SELECT login, password FROM users WHERE login=?";
    private static final String INSERT_USER = "INSERT INTO user (login, password, date_of_registration) VALUES(?, ?, ?)";



    private Connection getConnection(){
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

    public User findByLogin(String login) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement( FIND_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }finally {
            close(connection);
            close(preparedStatement);
        }

        return null;
    }

    public void insertUser(User user) throws  SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, String.valueOf(user.getDateOfRegistration())); // TODO
            preparedStatement.executeUpdate();

        }finally {
            close(connection);
            close(preparedStatement);
        }

    }


    private  void close(Connection connection){
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    private  void close(Statement statement){
        if(statement != null){
            try{
                statement.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

}
