package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;

public class AverageOfHeightCommand extends Command{
    private final CollectionManager collectionManager;

    public AverageOfHeightCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Object argOrData) {
        return new Response(collectionManager.averageHeight(),false);

    }
}
