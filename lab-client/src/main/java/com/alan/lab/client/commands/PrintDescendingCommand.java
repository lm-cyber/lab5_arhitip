package com.alan.lab.client.commands;


import com.alan.lab.client.utility.CollectionManager;



public class PrintDescendingCommand extends Command {

    private final CollectionManager collectionManager;

    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        return new CommandResult(false, collectionManager.descending().toString());
    }
}
