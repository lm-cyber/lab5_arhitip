package com.alan.lab.client.commands;


import com.alan.lab.client.utility.CollectionManager;

import java.util.Objects;


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


        if (collectionManager.getMainData().removeIf(x -> Objects.equals(x.getId(), longArg))) {
            return new CommandResult(false, "The element was deleted successfully.");
        } else {
            return new CommandResult(false, "Could not find written id. The command was not executed");
        }
    }
}
