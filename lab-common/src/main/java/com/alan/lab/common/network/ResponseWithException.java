package com.alan.lab.common.network;

public class ResponseWithException extends Response {

    private static final long serialVersionUID = 6717980015983933369L;
    private Exception e;

    public ResponseWithException(Exception e) {
        super("The server responded with an exception",false);
        this.e = e;
    }

    public Exception getException() {
        return e;
    }
}
