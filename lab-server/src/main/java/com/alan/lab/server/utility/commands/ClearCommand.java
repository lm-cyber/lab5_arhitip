package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.ResultOfSqlCollectionManager;
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
        ResultOfSqlCollectionManager result = sqlCollectionManager.clear();
        if (result.equals(ResultOfSqlCollectionManager.CLEAR_SUCCESS)) {
            collectionManager.clear();
            return new Response(result.toString(), false, true);
        }
        return new Response(result.toString(), false, true);
    }
}
