package com.vasivkov.chat.server.v2;

import com.vasivkov.chat.common.MessageTransportUtil;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class MessageSender implements Runnable {

    private BlockingQueue<ResponseWithRecipients> responses;
    private Map<Integer, ServerConnectionV2> connectedClients;

    public MessageSender(BlockingQueue<ResponseWithRecipients> responses, Map<Integer, ServerConnectionV2> connectedClients) {
        this.responses = responses;
        this.connectedClients = connectedClients;
    }

    @Override
    public void run() {
        while (true){
            ResponseWithRecipients responseWithRecipients = responses.poll();
            for (Integer id : responseWithRecipients.getRecipients()) {
               ServerConnectionV2 serverConnectionV2 =  connectedClients.get(id);
               MessageTransportUtil.sendMessageNoGuarantee(responseWithRecipients.getResponse(), serverConnectionV2.getOos());
            }
        }
    }
}
