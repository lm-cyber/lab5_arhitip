package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;

import java.util.Objects;

public abstract class Command {
    private String name;
    public Command(String name) {
        this.name =name;
    }


    public Response execute(Object argOrData) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;
        Command command = (Command) o;
        return name.equals(command.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
