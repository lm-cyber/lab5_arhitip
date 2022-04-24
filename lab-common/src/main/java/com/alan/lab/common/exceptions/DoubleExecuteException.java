package com.alan.lab.common.exceptions;

public class DoubleExecuteException extends UnsupportedOperationException {
    public DoubleExecuteException() {
        super("The file was not executed due to recursion");
    }
}
