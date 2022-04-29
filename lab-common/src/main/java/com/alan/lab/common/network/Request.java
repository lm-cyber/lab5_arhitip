package com.alan.lab.common.network;

import java.io.Serializable;

public class Request implements Serializable {


    private static final long serialVersionUID = 8241171356382541350L;
    private String commandName;
    private Object args;

    public Request(String commandName, Object args) {
        this.commandName = commandName;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public Object getArgs() {
        return args;
    }


}
