package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;

public class AddIfMinCommand extends CommandWithPerson {
    public AddIfMinCommand(CollectionManager collectionManager, SqlCollectionManager sqlCollectionManager) {
        super(collectionManager, sqlCollectionManager);
    }

    @Override
    public Response execute(Person person) {
        if (sqlCollectionManager.addIfMin(person)) {
            collectionManager.addMin(person);
            return new Response("add success", false, true);
        }
        return new Response("passport contains or not min", false, true);
    }
}
