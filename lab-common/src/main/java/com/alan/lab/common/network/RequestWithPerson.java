package com.alan.lab.common.network;

import com.alan.lab.common.data.Person;

import java.io.Serializable;

public class RequestWithPerson implements Serializable {
    private static final long serialVersionUID = -6312141303910282548L;
    private Person person;
    private RequestWithPersonType type;


    public RequestWithPerson(Person person ,RequestWithPersonType type) {
        this.person= person;
        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public RequestWithPersonType getType() {
        return type;
    }
}
