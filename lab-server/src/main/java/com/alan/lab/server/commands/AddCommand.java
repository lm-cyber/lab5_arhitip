package com.alan.lab.server.commands;


import com.alan.lab.common.data.Person;
import com.alan.lab.server.utility.CollectionManager;


public class AddCommand extends Command {
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add");
        this.collectionManager = collectionManager;
    }

    public CommandResult execute(Person person) {
        collectionManager.add(person);
        return new CommandResult(false, "success added");

    }

    @Override
    public CommandResult execute(String arg) {
        return null;
    }
}
