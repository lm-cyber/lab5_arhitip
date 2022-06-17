package com.alan.lab.server.utility.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class RemoveHeadCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Object arg, Long userID) {
        Response response;
        if (collectionManager.isEmpty()) {
            response = new Response("is empty ,cant remove_head", false,true);
        } else {

            Person person = collectionManager.poll(userID);
            if(person != null) {
                return new Response(person,false,true);
            }
        }
        return new Response("not owner",false,true);
    }
}

