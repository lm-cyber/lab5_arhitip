package com.alan.lab.common.network;

import java.io.Serializable;

public enum RequestWithPersonType implements Serializable {
    ADD("ADD"),
    UPDATE("UPDATE"),
    ADD_IF_MIN("ADD_IF_MIN"),
    NOT_VALUE("NOT_VALUE");
    private String value;

    RequestWithPersonType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }


    public static RequestWithPersonType getEnum(String value) {
        for (RequestWithPersonType v : values()) {
            if (v.getValue().equalsIgnoreCase(value)) {
                return v;
            }
        }
        return NOT_VALUE;
    }
}
