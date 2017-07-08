package com.vasivkov.chat.common;

/**
 * Created by vasya on 24/06/17.
 */
public class ClosedConnectionRequest extends Request {
    boolean close;

    public ClosedConnectionRequest(boolean close) {
        this.close = close;
    }

    public boolean isClose() {
        return close;
    }
}
