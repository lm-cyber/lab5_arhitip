package com.alan.lab.common.network;

import com.alan.lab.common.data.Person;

import java.io.Serializable;

public class RequestWithPerson implements Serializable {
    private static final long serialVersionUID = 8336802963880051781L;
    private Person person;


    public RequestWithPerson(Person person) {
        this.person= person;
    }

    public Person getPerson() {
        return person;
    }
}
