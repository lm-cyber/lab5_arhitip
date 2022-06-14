package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class RemoveHeadCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Object arg) {
        Response response;
        if (collectionManager.isEmpty()) {
            response = new Response("is empty ,cant remove_head", false);
        } else {

            response = new Response(collectionManager.poll(), false);
        }
        return response;
    }
}

