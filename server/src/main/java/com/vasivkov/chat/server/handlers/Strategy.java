package com.vasivkov.chat.server.handlers;

import com.vasivkov.chat.common.Request;
import com.vasivkov.chat.server.vo.ResponseWithRecipients;

import java.util.List;

public interface Strategy<T extends Request> {
   List<ResponseWithRecipients>  process(T t);
}
