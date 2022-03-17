package com.alan.lab.client.commands;


import com.alan.lab.client.utility.CollectionManager;

public class InfoCommand extends Command {

    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        return new CommandResult(false, "type:" + collectionManager.getMainData().getClass().toString()
                + "\ndate:" + collectionManager.getCreationDate().toString() + "\n"
                + "count_elem:" + collectionManager.getMainData().size() + "\n");
    }
}
