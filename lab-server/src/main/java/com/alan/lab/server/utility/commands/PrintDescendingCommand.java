package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;


public class PrintDescendingCommand extends Command {
    private final CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Object argOrData, Long userID) {
        return new Response(collectionManager.descending(), false, true);
    }
}
