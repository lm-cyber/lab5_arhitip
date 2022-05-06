package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;

public class AddCommand extends CommandWithPerson {
    public AddCommand(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Response execute(Person person, Long id) {
        if (collectionManager.add(person)) {
            return new Response("add success", false);
        }
        return new Response("contains passport", false);
    }
}
