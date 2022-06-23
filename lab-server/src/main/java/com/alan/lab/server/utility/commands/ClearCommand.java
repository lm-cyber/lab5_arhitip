package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;

public class ClearCommand extends  Command {
    private final CollectionManager collectionManager;
    private final SqlCollectionManager sqlCollectionManager;

    public ClearCommand(CollectionManager collectionManager, SqlCollectionManager sqlCollectionManager) {
        this.collectionManager = collectionManager;
        this.sqlCollectionManager = sqlCollectionManager;

    }

    @Override
    public Response execute(Object arg, Long userID) {
        if (sqlCollectionManager.clear()) {
            collectionManager.clear();
            return new Response("clear success", false, true);
        }
        return new Response("cat't clear",false,true);
    }
}
