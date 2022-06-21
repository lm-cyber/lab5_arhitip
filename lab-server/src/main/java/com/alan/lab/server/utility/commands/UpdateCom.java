package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class UpdateCom extends Command {
    private final CollectionManager collectionManager;

    public UpdateCom(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Object argOrData, Long userID) {
        if (argOrData instanceof Long) {
            if (collectionManager.isHaveId((Long) argOrData) && collectionManager.checkOwner((Long) argOrData, userID)) {
                return new Response("starting update ", true, true);
            }
            return new Response("havent id or you not owner", false, true);
        }
        return new Response("bad arg", false, true);
    }
}
