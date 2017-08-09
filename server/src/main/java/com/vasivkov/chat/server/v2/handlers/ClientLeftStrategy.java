package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.common.ClientLeftRequest;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageResponse;
import com.vasivkov.chat.server.dao.UserDao;
import com.vasivkov.chat.server.v2.Server;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientLeftStrategy implements Strategy<ClientLeftRequest>{
    private static final Logger LOGGER = Logger.getLogger(ClientLeftRequest.class.getName());
    private UserDao userDao = new UserDao();

    @Override
    public List<ResponseWithRecipients> process(ClientLeftRequest request) {
        int id = request.getId();
        String login = request.getClientLogin();
        Server.getConnectedClients().get(id).setConnected(false);
        Server.getConnectedClients().remove(id);
        LOGGER.info(id + " - " + login + ": deleted from chat");
        List<ResponseWithRecipients> responses = new ArrayList<>();
        responses.add(new ResponseWithRecipients(Server.getAuthorizedClients(id), new MessageResponse( new Message(login, "I'm LEFT THE CHAT!", new Date()))));
        return responses;
    }
}
