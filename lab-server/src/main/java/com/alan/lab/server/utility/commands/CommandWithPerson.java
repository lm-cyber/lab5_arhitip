package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;

public abstract class CommandWithPerson {
    protected final CollectionManager collectionManager;
    protected final SqlCollectionManager sqlCollectionManager;


    public CommandWithPerson(CollectionManager collectionManager, SqlCollectionManager sqlCollectionManager) {
        this.collectionManager = collectionManager;
        this.sqlCollectionManager = sqlCollectionManager;
    }


    public Response execute(Person person) {
        return null;
    }
}
