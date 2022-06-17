package com.alan.lab.common.network;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 7245430527055132764L;
    private Object message;
    private boolean addsCommand;

    private boolean authBoolean;
    public Response(Object message, boolean addsCommand) {
        this.message = message;
        this.addsCommand = addsCommand;
    }

    public Response(Object message, boolean addsCommand, boolean authBoolean) {
        this.message = message;
        this.addsCommand = addsCommand;
        this.authBoolean = authBoolean;
    }

    public boolean getAuthBoolean() {
        return authBoolean;
    }
    public boolean getAddsCommand() {
        return addsCommand;
    }

    public Object getMessage() {
        return message;
    }
}
