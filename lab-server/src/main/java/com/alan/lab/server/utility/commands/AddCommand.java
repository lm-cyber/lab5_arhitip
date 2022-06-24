package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.ResultOfSqlCollectionManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;

public class AddCommand extends CommandWithPerson {
    public AddCommand(CollectionManager collectionManager, SqlCollectionManager sqlCollectionManager) {
        super(collectionManager, sqlCollectionManager);
    }

    @Override
    public Response execute(Person person) {
        ResultOfSqlCollectionManager result = sqlCollectionManager.add(person);
        if (result.equals(ResultOfSqlCollectionManager.ADD_SUCCESS)) {
            collectionManager.add(person);
            return new Response(result.toString(), false, true);
        }
        return new Response(result.toString(), false, true);
    }
}
