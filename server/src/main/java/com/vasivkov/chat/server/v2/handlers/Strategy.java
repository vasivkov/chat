package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.server.v2.ServerConnectionV2;
import com.vasivkov.chat.server.v2.vo.Request;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;

public interface Strategy<T extends Request> {
    ResponseWithRecipients process(T t);
}
