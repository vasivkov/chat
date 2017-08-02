package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.server.ServerConnection;
import com.vasivkov.chat.server.dao.MessageDao;
import com.vasivkov.chat.server.v2.ServerConnectionV2;
import com.vasivkov.chat.server.v2.ServerV2;
import com.vasivkov.chat.server.v2.vo.Message;
import com.vasivkov.chat.server.v2.vo.MessageRequest;
import com.vasivkov.chat.server.v2.vo.MessageResponse;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

public class MessageStrategy implements Strategy<MessageRequest> {
    MessageDao messageDao = new MessageDao();
    private static final Logger LOGGER = Logger.getLogger(ServerConnection.class.getName());

    @Override
    public List<ResponseWithRecipients> process(MessageRequest request) {
        List<Integer> listOfResipients = new ArrayList<>();
        List<ResponseWithRecipients> listOfResponses = new ArrayList<>();
        Message message = request.getMessage();

        for (Map.Entry<Integer, ServerConnectionV2> pair : ServerV2.getConnectedClients().entrySet()) {
            if (pair.getKey() != request.getId() && pair.getValue().isActive()) {
                listOfResipients.add(pair.getKey());
            }
        }
        try {
            messageDao.insertMessage(message);
        } catch (SQLException e) {
        LOGGER.error("Failed to save message from " + message.getAuthor() + " in database.");
        }
        listOfResponses.add(new ResponseWithRecipients(listOfResipients, new MessageResponse(message)));
        return listOfResponses;
    }
}
