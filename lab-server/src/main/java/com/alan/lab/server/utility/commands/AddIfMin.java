package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.exceptions.NotMinException;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;

public class AddIfMin extends CommandWithPerson{
    public AddIfMin(CollectionManager collectionManager) {
        super(collectionManager);
    }

    @Override
    public Response execute(Person person, Long id) {
         try {
            if (collectionManager.addMin(person)) {
                return new Response("add success", false);
            }
            return new Response("passport contains", false);
        } catch (NotMinException e) {
            return new Response(e.getMessage(), false);


        }
    }
}
