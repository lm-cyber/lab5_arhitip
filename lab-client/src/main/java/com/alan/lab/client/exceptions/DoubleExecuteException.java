package com.alan.lab.client.exceptions;

public class DoubleExecuteException extends UnsupportedOperationException{
    public DoubleExecuteException(){
        super("The file was not executed due to recursion");
    }
}
