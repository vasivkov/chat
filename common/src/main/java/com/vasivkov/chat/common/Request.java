package com.vasivkov.chat.common;

import java.io.Serializable;

public abstract class Request implements Serializable{
    public abstract String getLogin();
}

