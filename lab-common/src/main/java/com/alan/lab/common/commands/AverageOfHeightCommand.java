package com.alan.lab.common.commands;

import com.alan.lab.common.utility.CollectionManager;


public class AverageOfHeightCommand extends Command {
    private CollectionManager collectionManager;

    public AverageOfHeightCommand(CollectionManager collectionManager) {
        super("average_of_height");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        return new CommandResult(false, collectionManager.averageHeight().toString());
    }
}
