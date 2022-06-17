package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.exceptions.NotMinException;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class AddIfMinCommand extends CommandWithPerson {
    public AddIfMinCommand(CollectionManager collectionManager, SqlUserManager sqlUserManager) {
        super(collectionManager, sqlUserManager);
    }

    @Override
    public Response execute(Person person, Long id) {
        try {
            if (collectionManager.addMin(person)) {
                return new Response("add success", false, true);
            }
            return new Response("passport contains", false, true);
        } catch (NotMinException e) {
            return new Response(e.getMessage(), false, true);


        }
    }
}
