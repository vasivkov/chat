package com.vasivkov.chat.server.dao;

import com.vasivkov.chat.common.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MessageDao extends ChatAbstractDao {
    private static final String INSERT_MESSAGE = "INSERT INTO messages (text, message_datetime, author) VALUES (?, ?, ?)";
    private static final String GET_MESSAGE = "SELECT text, message_datetime, author FROM messages ORDER BY message_datetime DESC LIMIT 10";
    public void insertMessage(Message message) throws SQLException {
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

    public List<Message> getLastTenMessages() throws SQLException {
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

}
