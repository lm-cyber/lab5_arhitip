package com.alan.lab.common.network;

import java.io.Serializable;

public class Request extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = 4643797287519810631L;


    public Request(String commandName, String args) {
        super(commandName,args);
    }


}
