package com.alan.lab.client.commands;


import com.alan.lab.client.utility.CollectionManager;

import java.util.StringJoiner;


public class ShowCommand extends Command {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StringJoiner output = new StringJoiner("\n\n");

        return new CommandResult(false, collectionManager.getMainData().toString());
    }
}
