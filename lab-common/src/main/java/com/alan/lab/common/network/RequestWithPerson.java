package com.alan.lab.common.network;

import com.alan.lab.common.data.Person;

import java.io.Serializable;

public class RequestWithPerson extends Request implements Serializable {
    private Person person;


    public RequestWithPerson(String commandName, String args,Person person) {
        super(commandName, args);
        this.person= person;
    }

    public Person getPerson() {
        return person;
    }
}
