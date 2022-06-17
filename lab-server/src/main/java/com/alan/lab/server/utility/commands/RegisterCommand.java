package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.common.users.AuthCredentials;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;

public class RegisterCommand extends Command {

    private final SqlUserManager sqlUserManager;

    public RegisterCommand(SqlUserManager sqlUserManager) {
        this.sqlUserManager = sqlUserManager;
    }

    @Override
    public Response execute(Object argOrData, Long userID) {
        if(sqlUserManager.register((AuthCredentials) argOrData) != null) {
            return new Response("success reg",false,true);
        }
        return new Response("not reg",false,false);
    }
}
