package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.server.dao.MessageDao;
import com.vasivkov.chat.server.v2.ServerV2;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageRequest;
import com.vasivkov.chat.common.MessageResponse;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.*;

public class MessageStrategy implements Strategy<MessageRequest> {
    private MessageDao messageDao = new MessageDao();
    private  final Logger LOGGER = Logger.getLogger(MessageStrategy.class.getName());

    @Override
    public List<ResponseWithRecipients> process(MessageRequest request) {
        List<ResponseWithRecipients> listOfResponses = new ArrayList<>();
        Message message = request.getMessage();
        try {
            messageDao.insertMessage(message);
        } catch (SQLException e) {
        LOGGER.error("Failed to save message from " + message.getAuthor() + " in database.");
        }
        listOfResponses.add(new ResponseWithRecipients(ServerV2.getAuthorizedClients(request.getId()), new MessageResponse(message)));
        return listOfResponses;
    }
}
