package com.alan.lab.client.commands;

import com.alan.lab.client.utility.CollectionManager;

public class RemoveHeadCommand extends Command {
    private CollectionManager collectionManager;

    public RemoveHeadCommand(CollectionManager collectionManager) {
        super("remove_head");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResult(false, "collection is empty");
        }
        return new CommandResult(false, "success delete");
    }
}
