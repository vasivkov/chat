package com.vasivkov.chat.client;


import java.util.Arrays;

public enum ClientCommands {
    REGISTRATION("R"),
    AUTHORIZATION("A"),
    QUIT(":Q"),
    UNKNOWN(null);

    private String command;

    ClientCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static ClientCommands of(String command) {
        return Arrays.stream(ClientCommands.values())
                .filter(c -> command.equals(c.getCommand()))
                .findFirst().orElse(UNKNOWN);
    }
}
