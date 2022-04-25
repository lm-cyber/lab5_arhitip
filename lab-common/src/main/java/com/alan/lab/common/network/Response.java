package com.alan.lab.common.network;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 299712538080526778L;
    private String message = "";

    private boolean addsCommand;
    public Response(String message,boolean addsCommand) {
        this.addsCommand  = addsCommand;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
