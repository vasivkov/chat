package com.vasivkov.chat.server.v2.vo;

import java.io.Serializable;

public abstract class Request implements Serializable {
    private int ID;

    abstract String getSender();

    public int getId() {
        return ID;
    }

    public void setID(int ID) {

        this.ID = ID;
    }
}
