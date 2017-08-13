package com.vasivkov.chat.server.handlers;


import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageResponse;
import com.vasivkov.chat.server.dao.MessageDao;
import com.vasivkov.chat.server.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbstractStrategy {
    private static final Logger LOGGER = Logger.getLogger(AbstractStrategy.class.getName());

    private MessageDao messageDao = new MessageDao();

    public List<ResponseWithRecipients> getLastMessages(int id) {
        List<ResponseWithRecipients> responseWithRecipients = new ArrayList<>();
        List<Message> messages = Collections.EMPTY_LIST;
        try {
            messages = messageDao.getLastTenMessages();
        } catch (SQLException e) {
            LOGGER.error("Failed to write messages from database", e);
        }
        for (Message message : messages) {
            responseWithRecipients.add(new ResponseWithRecipients(id, new MessageResponse(message)));
        }
        return responseWithRecipients;
    }
}
