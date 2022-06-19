package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class UpdateCommand extends CommandWithPerson {
    public UpdateCommand(CollectionManager collectionManager, SqlUserManager sqlUserManager, SqlCollectionManager sqlCollectionManager) {
        super(collectionManager, sqlUserManager, sqlCollectionManager);
    }

    @Override
    public Response execute(Person person, Long id, Long userID) {
        person.setId(id);
        if (collectionManager.update(person, id, userID)) {
            sqlCollectionManager.update(person);
            return new Response("add success", false, true);
        }
        return new Response("contains passport or you not ownerq", false, true);
    }
}
