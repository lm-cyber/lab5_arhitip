package com.alan.lab.common.network;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.users.AuthCredentials;

import java.io.Serializable;

public class RequestWithPerson implements Serializable {
    private static final long serialVersionUID = -6312141303910282548L;
    private Person person;
    private RequestWithPersonType type;
    private AuthCredentials authCredentials;


    public RequestWithPerson(Person person, RequestWithPersonType type, AuthCredentials authCredentials) {
        this.person = person;
        this.type = type;
        this.authCredentials = authCredentials;
    }

    public AuthCredentials getAuthCredentials() {
        return authCredentials;
    }

    public Person getPerson() {
        return person;
    }

    public RequestWithPersonType getType() {
        return type;
    }
}
