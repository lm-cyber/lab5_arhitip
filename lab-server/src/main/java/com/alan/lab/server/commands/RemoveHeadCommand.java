package com.alan.lab.server.commands;

import com.alan.lab.server.utility.CollectionManager;

public class RemoveHeadCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        super("remove_head");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        if (collectionManager.isEmpty()) {
            return new CommandResult(false, "collection is empty");
        }
        return new CommandResult(false, collectionManager.poll().toString() + "success delete");
    }
}
