package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class FilterGreaterThanHeightCommand extends Command {
    private final CollectionManager collectionManager;

    public FilterGreaterThanHeightCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Object argOrData, Long userID) {
        return new Response(collectionManager.filterGreaterThanHeight((Float) argOrData), false, true);
    }
}
