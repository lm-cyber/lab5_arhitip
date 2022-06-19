package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.exceptions.NotMinException;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class AddIfMinCommand extends CommandWithPerson {
    public AddIfMinCommand(CollectionManager collectionManager, SqlUserManager sqlUserManager, SqlCollectionManager sqlCollectionManager) {
        super(collectionManager, sqlUserManager, sqlCollectionManager);
    }

    @Override
    public Response execute(Person person, Long id, Long userID) {
        try {
            if (collectionManager.addMin(person, userID)) {
                sqlCollectionManager.add(person);
                return new Response("add success", false, true);
            }
            return new Response("passport contains", false, true);
        } catch (NotMinException e) {
            return new Response(e.getMessage(), false, true);


        }
    }
}
