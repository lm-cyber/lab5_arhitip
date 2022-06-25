package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;

import java.sql.SQLException;

public class UpdateCom extends Command {
    private final CollectionManager collectionManager;
    private final SqlCollectionManager sqlCollectionManager;

    public UpdateCom(CollectionManager collectionManager, SqlCollectionManager sqlCollectionManager) {
        this.collectionManager = collectionManager;
        this.sqlCollectionManager = sqlCollectionManager;
    }

    @Override
    public Response execute(Object argOrData, Long userID) {
        if (argOrData instanceof Long) {
            try {
                if (sqlCollectionManager.update((Long) argOrData, userID)) {
                    return new Response("starting update ", true, true);
                }
            } catch (SQLException e) {
                return new Response("db dead", false, true);
            }
            return new Response("havent id or you not owner", false, true);
        }
        return new Response("bad arg", false, true);
    }
}
