package com.alan.lab.common.network;

public abstract class AbstractRequest {
    private String commandName;
    private String args;

    public AbstractRequest(String commandName, String args) {
        this.commandName = commandName;
        this.args = args;
    }
    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }

}
