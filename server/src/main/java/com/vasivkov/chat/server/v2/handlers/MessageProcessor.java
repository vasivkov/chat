package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.common.ClientLeftRequest;
import com.vasivkov.chat.common.Request;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MessageProcessor implements Runnable {

    private BlockingQueue<Request> requests;
    private BlockingQueue<ResponseWithRecipients> responses;
    private static final Logger LOGGER = Logger.getLogger(ClientLeftRequest.class.getName());

    public MessageProcessor(BlockingQueue<Request> requests, BlockingQueue<ResponseWithRecipients> responses) {
        this.requests = requests;
        this.responses = responses;
    }

    @Override
    public void run() {
        while(true) {
            Request next = null;
            try {
                next = requests.take();
            } catch (InterruptedException e) {
                LOGGER.error("Thread MessageProcessor was interrupted", e);
            }
            Strategy s = StrategyFactory.getStrategy(next);
            List<ResponseWithRecipients> listOfResponses = s.process(next);
            for(ResponseWithRecipients response: listOfResponses){
                responses.add(response);
            }
        }
    }

}
