package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;

public abstract class CommandWithPerson {
    protected final CollectionManager collectionManager;

    public CommandWithPerson(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    public Response execute(Person person, Long id) {
        return null;
    }
}
