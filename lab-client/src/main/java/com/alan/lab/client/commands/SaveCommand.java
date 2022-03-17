package com.alan.lab.client.commands;


import com.alan.lab.client.utility.CollectionManager;
import com.alan.lab.client.utility.FileManager;
import com.alan.lab.client.utility.JsonParser;

import java.io.FileNotFoundException;

public class SaveCommand extends Command {
    private final FileManager fileManager;
    private final CollectionManager collectionManager;

    public SaveCommand(FileManager fileManager, CollectionManager collectionManager) {
        super("save");
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        try {
            fileManager.write(JsonParser.toJson(collectionManager.getMainData()));
        } catch (FileNotFoundException e) {
            return new CommandResult(false, "There was a problem saving a file. Please restart the program with another one");
        }
        return new CommandResult(false, "The data was saved successfully");
    }
}
