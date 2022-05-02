package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;

public class InfoCommand extends Command{
    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    @Override
    public Response execute(Object arg) {
        return new Response("type:" + collectionManager.getType().toString()
                        + "\ndate:" + collectionManager.getCreationDate().toString() + "\n"
                        + "count_elem:" + collectionManager.getSize() + "\n", false);
    }
}
