package com.vasivkov.chat.server.v2.handlers;

import com.vasivkov.chat.common.Request;
import com.vasivkov.chat.server.v2.vo.ResponseWithRecipients;

import java.util.List;

public interface Strategy<T extends Request> {
   List<ResponseWithRecipients>  process(T t);
}
