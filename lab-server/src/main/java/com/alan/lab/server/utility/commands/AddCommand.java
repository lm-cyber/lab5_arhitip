package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class AddCommand extends CommandWithPerson {
    public AddCommand(CollectionManager collectionManager, SqlUserManager sqlUserManager) {
        super(collectionManager, sqlUserManager);
    }

    @Override
    public Response execute(Person person, Long id) {
        if (collectionManager.add(person)) {
            return new Response("add success", false, true);
        }
        return new Response("contains passport", false, true);
    }
}
