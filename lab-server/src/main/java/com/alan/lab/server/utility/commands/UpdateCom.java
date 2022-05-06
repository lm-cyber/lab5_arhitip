package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;

public class UpdateCom extends Command{
    private final CollectionManager collectionManager;

    public UpdateCom(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Object argOrData) {
        if (argOrData instanceof Long) {
            if (collectionManager.isHaveId((Long) argOrData)) {
                return new Response("starting update ", true);
            }
            return new Response("havent id", false);
        }
        return new Response("bad arg",false);
    }
}
