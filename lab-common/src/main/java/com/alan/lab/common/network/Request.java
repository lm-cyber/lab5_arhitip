package com.alan.lab.common.network;

import com.alan.lab.common.users.AuthCredentials;

import java.io.Serializable;

public class Request implements Serializable {


    private static final long serialVersionUID = 8241171356382541350L;
    private String commandName;
    private Object args;
    private AuthCredentials authCredentials;
    public Request(String commandName, Object args, AuthCredentials authCredentials) {
        this.commandName = commandName;
        this.args = args;
        this.authCredentials = authCredentials;
    }

    public AuthCredentials getAuthCredentials() {
        return authCredentials;
    }

    public String getCommandName() {
        return commandName;
    }

    public Object getArgs() {
        return args;
    }


}
