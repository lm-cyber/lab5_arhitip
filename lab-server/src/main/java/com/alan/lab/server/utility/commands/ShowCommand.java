package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;

public class ShowCommand extends Command{

    private final CollectionManager collectionManager;
    public ShowCommand( CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(Object arg) {
        return new Response(collectionManager.toString(),false);
    }
}