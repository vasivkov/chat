package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.common.ClientLeftRequest;
import com.vasivkov.chat.common.GeneralResponse;
import com.vasivkov.chat.common.Message;
import com.vasivkov.chat.common.MessageResponse;
import com.vasivkov.chat.server.dao.UserDao;
import com.vasivkov.chat.server.v2.ServerV2;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ClientLeftStrategy implements Strategy<ClientLeftRequest>{
    private static final Logger LOGGER = Logger.getLogger(ClientLeftRequest.class.getName());
    private UserDao userDao = new UserDao();

    @Override
    public List<ResponseWithRecipients> process(ClientLeftRequest request) {
        int id = request.getId();
        String login = request.getClientLogin();
        ServerV2.getConnectedClients().get(id).setAuthorized(false);
        List<ResponseWithRecipients> responses = new ArrayList<>();
        responses.add(new ResponseWithRecipients(id, new GeneralResponse(true, "")));
        responses.add(new ResponseWithRecipients(ServerV2.getAuthorizedClients(id), new MessageResponse( new Message( "   " + login + " left the chat"))));
        return responses;
    }
}
