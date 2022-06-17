package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;

public class AddCom extends Command {
    @Override
    public Response execute(Object argOrData, Long userID) {
        return new Response("add start", true, true);
    }
}
