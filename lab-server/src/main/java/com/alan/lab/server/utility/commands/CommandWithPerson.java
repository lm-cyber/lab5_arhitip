package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.common.users.AuthCredentials;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public abstract class CommandWithPerson {
    protected final CollectionManager collectionManager;
    protected final SqlCollectionManager sqlCollectionManager;
    private final SqlUserManager sqlUserManager;


    public CommandWithPerson(CollectionManager collectionManager, SqlUserManager sqlUserManager, SqlCollectionManager sqlCollectionManager) {
        this.collectionManager = collectionManager;
        this.sqlUserManager = sqlUserManager;
        this.sqlCollectionManager = sqlCollectionManager;
    }

    protected Long haveUserRights(AuthCredentials authCredentials) {
        return sqlUserManager.authenticate(authCredentials);
    }

    public Response execute(Person person, Long id, Long userID) {
        return null;
    }
}
