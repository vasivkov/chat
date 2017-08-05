package com.vasivkov.chat.common;

import java.io.Serializable;

public abstract class Request implements Serializable {
    private int id;

    abstract String getSender();

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

}
