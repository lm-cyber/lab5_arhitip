package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.ResultOfSqlCollectionManager;
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
        ResultOfSqlCollectionManager result;
        ResultOfSqlCollectionManager resultEmpty = sqlCollectionManager.isEmpty();
        if (resultEmpty.equals(ResultOfSqlCollectionManager.IS_EMPTY_TRUE)) {
            return new Response(resultEmpty.toString(), false, true);
        } else {

            Person person = collectionManager.peek();
            result = sqlCollectionManager.remove(person.getId(), userID);
            if (result.equals(ResultOfSqlCollectionManager.REMOVE_SUCCESS)) {
                collectionManager.poll(person.getOwnerID());
                return new Response(person, false, true);
            }
        }
        return new Response(result.toString(), false, true);
    }
}

