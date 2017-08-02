package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.server.dao.MessageDao;
import com.vasivkov.chat.server.v2.ServerConnectionV2;
import com.vasivkov.chat.server.v2.ServerV2;
import com.vasivkov.chat.server.v2.vo.Message;
import com.vasivkov.chat.server.v2.vo.MessageRequest;
import com.vasivkov.chat.server.v2.vo.MessageResponse;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MessageStrategy implements Strategy<MessageRequest> {
    MessageDao messageDao = new MessageDao();

    @Override
    public ResponseWithRecipients process(MessageRequest request) {
        List<Integer> listOfResipients = new ArrayList<>();
        Message message = request.getMessage();
        try {
            messageDao.insertMessage(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Integer, ServerConnectionV2> pair : ServerV2.getConnectedClients().entrySet()) {
            if (pair.getKey() != request.getId() && pair.getValue().isActive()) {
                listOfResipients.add(pair.getKey());
            }
        }
        try {
            messageDao.insertMessage(message);
        } catch (SQLException e) {

        }
        //TODO
        // save to database
        return new ResponseWithRecipients(listOfResipients, new MessageResponse(message));
    }
}
