package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;

public class RemoveHeadCommand extends Command {
    private final CollectionManager collectionManager;
    private final SqlCollectionManager sqlCollectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager, SqlCollectionManager sqlCollectionManager) {
        this.collectionManager = collectionManager;
        this.sqlCollectionManager = sqlCollectionManager;

    }

    @Override
    public Response execute(Object arg, Long userID) {
        Response response;
        if (collectionManager.isEmpty()) {
            response = new Response("is empty ,cant remove_head", false, true);
        } else {

            Person person = collectionManager.poll(userID);
            if (person != null) {
                sqlCollectionManager.remove(person.getId());
                return new Response(person, false, true);
            }
        }
        return new Response("not owner", false, true);
    }
}

