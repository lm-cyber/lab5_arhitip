package com.alan.lab.common.commands;

import com.alan.lab.common.utility.CollectionManager;
import com.alan.lab.common.utility.OutputManager;

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
