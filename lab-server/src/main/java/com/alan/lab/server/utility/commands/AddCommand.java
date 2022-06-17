package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;

public class AddCommand extends CommandWithPerson {
    public AddCommand(CollectionManager collectionManager, SqlUserManager sqlUserManager) {
        super(collectionManager, sqlUserManager);
    }

    @Override
    public Response execute(Person person, Long id, Long userID) {
        if (collectionManager.add(person,userID)) {
            return new Response("add success", false, true);
        }
        return new Response("contains passport", false, true);
    }
}
