package com.alan.lab.common.commands;


import com.alan.lab.common.utility.CollectionManager;

public class ClearCommand extends Command {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        collectionManager.clear();
        return new CommandResult(false, "The collection was cleared successfully.");
    }
}
