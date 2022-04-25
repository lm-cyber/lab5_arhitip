package com.alan.lab.server.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.exceptions.NotMinException;
import com.alan.lab.server.utility.CollectionManager;


public class AddIfMinCommand extends Command {
    private final CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min");
        this.collectionManager = collectionManager;
    }

    public CommandResult execute(Person person) {
        try {
            collectionManager.addMin(person);
            return new CommandResult(false, "success added");
        } catch (NotMinException e) {
            return new CommandResult(false, "not success:" + e.getMessage());
        }

    }

    @Override
    public CommandResult execute(String arg) {
        return null;
    }
}
