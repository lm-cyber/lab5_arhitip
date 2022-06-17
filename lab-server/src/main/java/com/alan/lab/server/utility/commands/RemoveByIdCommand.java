package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class RemoveByIdCommand extends Command {

    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(Object arg, Long userID) {
        Response response;
        if (collectionManager.removeByID((Long) arg,userID)) {
            response = new Response("delete success", false, true);
        } else {
            response = new Response("havent id", false, true);
        }
        return response;
    }
}
