package com.alan.lab.common.network;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 4643797287519810631L;
    private String commandName;
    private String args;

    public Request(String commandName, String args) {
        this.commandName = commandName;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

}
