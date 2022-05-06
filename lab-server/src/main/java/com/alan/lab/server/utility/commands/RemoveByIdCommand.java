package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;

public class RemoveByIdCommand extends Command {

    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public Response execute(Object arg) {
        Response response;
        if (!collectionManager.removeByID((Long) arg)) {
            response = new Response("delete success", false);
        } else {
            response = new Response("havent id", false);
        }
        return response;
    }
}
