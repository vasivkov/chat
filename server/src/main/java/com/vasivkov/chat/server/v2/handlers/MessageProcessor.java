package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.server.v2.vo.Request;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;

import java.util.concurrent.BlockingQueue;

public class MessageProcessor implements Runnable {

    private BlockingQueue<Request> requests;
    private BlockingQueue<ResponseWithRecipients> responses;

    public MessageProcessor(BlockingQueue<Request> requests, BlockingQueue<ResponseWithRecipients> responses) {
        this.requests = requests;
        this.responses = responses;
    }

    @Override
    public void run() {
        while(true) {
            Request next = requests.poll();
            Strategy s = StrategyFactory.getStrategy(next);
            ResponseWithRecipients response = s.process(next);
            responses.add(response);
        }
    }

}
