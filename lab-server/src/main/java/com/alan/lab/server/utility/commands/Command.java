package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;

public abstract class Command {
    public Response execute(Object argOrData, Long userID) {
        return null;
    }

}
