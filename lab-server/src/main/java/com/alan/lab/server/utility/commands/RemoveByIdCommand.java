package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;

public class RemoveByIdCommand extends Command {

    private final CollectionManager collectionManager;
    private final SqlCollectionManager sqlCollectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager, SqlCollectionManager sqlCollectionManager) {
        this.collectionManager = collectionManager;
        this.sqlCollectionManager = sqlCollectionManager;
    }
    @Override
    public Response execute(Object arg, Long userID) {
        Response response;
        if (collectionManager.removeByID((Long) arg,userID)) {
            sqlCollectionManager.remove((Long) arg);
            response = new Response("delete success", false, true);
        } else {
            response = new Response("havent id", false, true);
        }
        return response;
    }
}
