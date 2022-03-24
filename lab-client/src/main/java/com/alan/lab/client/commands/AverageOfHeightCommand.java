package com.alan.lab.client.commands;

import com.alan.lab.client.data.Person;
import com.alan.lab.client.utility.CollectionManager;

import java.util.OptionalDouble;

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
