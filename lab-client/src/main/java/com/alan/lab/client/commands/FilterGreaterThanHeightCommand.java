package com.alan.lab.client.commands;



import com.alan.lab.client.utility.CollectionManager;

import java.util.stream.Collectors;

public class FilterGreaterThanHeightCommand extends Command {

    private final CollectionManager collectionManager;

    public FilterGreaterThanHeightCommand(CollectionManager collectionManager) {
        super("filter_greater_than_height");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {

        return new CommandResult(false, collectionManager.getMainData().stream().filter(person -> {
            return person.getHeight() > Float.parseFloat(arg);
        }).collect(Collectors.toList()).toString());
    }
}
