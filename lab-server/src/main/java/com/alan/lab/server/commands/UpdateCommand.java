package com.alan.lab.server.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.server.utility.CollectionManager;

public class UpdateCommand extends Command {
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update");
        this.collectionManager = collectionManager;
    }

    public CommandResult execute(String arg,Person person) {
        Long id;
        try {
            id = Long.parseLong(arg);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Your argument was incorrect. The command was not executed.");
        }
        if (!collectionManager.isHaveId(id)) {
            return new CommandResult(false, "have not this id");
        }

        person.setId(id);
        collectionManager.removeByID(id);
        collectionManager.add(person);
        return new CommandResult(false, "success added");

    }

    @Override
    public CommandResult execute(String arg) {
        return null;
    }
}
