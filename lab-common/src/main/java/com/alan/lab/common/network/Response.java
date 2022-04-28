package com.alan.lab.common.network;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = -6966872113588366076L;
    private String message = "";
    private boolean addsCommand;
    public Response(String message,boolean addsCommand) {
        this.message = message;
        this.addsCommand =addsCommand;
    }
    public boolean getAddsCommand() {
        return addsCommand;
    }

    public String getMessage() {
        return message;
    }
}
