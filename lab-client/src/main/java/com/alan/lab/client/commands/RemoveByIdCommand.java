package com.alan.lab.client.commands;


import com.alan.lab.client.data.Person;
import com.alan.lab.client.utility.CollectionManager;

import java.util.Optional;


public class RemoveByIdCommand extends Command {
    private final CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        Long longArg;
        try {
            longArg = Long.parseLong(arg);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Your argument was incorrect. The command was not executed.");
        }

        Optional<Person> optionalPerson = collectionManager.getMainData().stream().filter(x -> x.getId().equals(longArg)).findAny();
        if (optionalPerson.isPresent()) {
            collectionManager.delete(optionalPerson.get());
            return new CommandResult(false, "success delete");
        }
        return new CommandResult(false, "not person with id");

    }
}
