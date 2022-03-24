package com.alan.lab.client.commands;

import com.alan.lab.client.utility.CollectionManager;
import com.alan.lab.client.utility.OutputManager;

public class RemoveHeadCommand extends Command {
    private CollectionManager collectionManager;
    private OutputManager outputManager;

    public RemoveHeadCommand(CollectionManager collectionManager, OutputManager outputManager) {
        super("remove_head");
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        if (collectionManager.isEmpty()) {
            return new CommandResult(false, "collection is empty");
        }
        return new CommandResult(false, collectionManager.poll().toString() + "success delete");
    }
}
