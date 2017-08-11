package com.vasivkov.chat.server;

import com.vasivkov.chat.common.MessageTransportUtil;
import com.vasivkov.chat.server.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class MessageSender implements Runnable {

    private BlockingQueue<ResponseWithRecipients> responses;
    private Map<Integer, ServerConnection> connectedClients;
    private static final Logger LOGGER = Logger.getLogger(MessageSender.class.getName());

    public MessageSender(BlockingQueue<ResponseWithRecipients> responses, Map<Integer, ServerConnection> connectedClients) {
        this.responses = responses;
        this.connectedClients = connectedClients;
    }

    @Override
    public void run() {
        while (true) {
            ResponseWithRecipients responseWithRecipients = null;
            try {
                responseWithRecipients = responses.take();
            } catch (InterruptedException e) {
                LOGGER.error("Thread MessageSender was interrupted", e);
            }
            for (Integer id : responseWithRecipients.getRecipients()) {
                ServerConnection serverConnection = connectedClients.get(id);
                MessageTransportUtil.sendMessageNoGuarantee(responseWithRecipients.getResponse(), serverConnection.getOos());
            }
        }
    }
}
