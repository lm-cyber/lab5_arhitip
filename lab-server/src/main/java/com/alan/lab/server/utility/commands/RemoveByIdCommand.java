package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.ResultOfSqlCollectionManager;
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
        ResultOfSqlCollectionManager result = sqlCollectionManager.remove((Long) arg, userID);
        if (result.equals(ResultOfSqlCollectionManager.REMOVE_SUCCESS)) {
            collectionManager.removeByID((Long) arg, userID);
            return new Response(result.toString(), false, true);
        }
        return new Response(result.toString(), false, true);
    }
}
