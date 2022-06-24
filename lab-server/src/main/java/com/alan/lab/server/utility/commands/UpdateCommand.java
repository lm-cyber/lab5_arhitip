package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.ResultOfSqlCollectionManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;

public class UpdateCommand extends CommandWithPerson {
    public UpdateCommand(CollectionManager collectionManager, SqlCollectionManager sqlCollectionManager) {
        super(collectionManager, sqlCollectionManager);
    }

    @Override
    public Response execute(Person person) {
        ResultOfSqlCollectionManager result = sqlCollectionManager.update(person);
        if (result.equals(ResultOfSqlCollectionManager.UPDATE_SUCCESS)) {
            collectionManager.update(person);
            return new Response(result.toString(), false, true);
        }
        return new Response(result.toString(), false, true);
    }
}
