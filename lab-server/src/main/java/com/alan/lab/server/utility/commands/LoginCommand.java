package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.common.users.AuthCredentials;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;

public class LoginCommand extends Command {
    private final SqlUserManager sqlUserManager;

    public LoginCommand(SqlUserManager sqlUserManager) {
        this.sqlUserManager = sqlUserManager;
    }

    @Override
    public Response execute(Object argOrData, Long userID) {
        if (sqlUserManager.authenticate((AuthCredentials) argOrData) != null) {
            return new Response("success auth", false, true);
        }
        return new Response("not auth", false, false);
    }
}
