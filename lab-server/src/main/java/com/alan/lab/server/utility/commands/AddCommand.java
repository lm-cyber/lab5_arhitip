package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;

public class AddCommand extends CommandWithPerson {
    public AddCommand(CollectionManager collectionManager, SqlCollectionManager sqlCollectionManager) {
        super(collectionManager, sqlCollectionManager);
    }

    @Override
    public Response execute(Person person) {
        if (sqlCollectionManager.add(person)) {
            collectionManager.add(person);
            return new Response("add success", false, true);
        }
        return new Response("contains passport", false, true);
    }
}
