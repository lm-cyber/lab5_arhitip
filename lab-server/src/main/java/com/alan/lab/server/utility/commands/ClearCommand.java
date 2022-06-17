package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class ClearCommand extends  Command {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;

    }

    @Override
    public Response execute(Object arg, Long userID) {
        collectionManager.clear();
        return new Response("clear success", false, true);
    }
}
