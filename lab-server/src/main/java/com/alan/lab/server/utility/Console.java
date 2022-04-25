package com.alan.lab.server.utility;

import com.alan.lab.common.data.Person;
import com.alan.lab.server.commands.CommandResult;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.PriorityQueue;


/**
 * The main class for app to be run
 */
public class Console {
    private final CommandRunManager commandRunManager;

    private final CollectionManager collectionManager;
    private final FileManager fileManager;

    public Console(FileManager fileManager,
                   CollectionManager collectionManager, CommandRunManager commandRunManager) {
        this.fileManager = fileManager;
        this.collectionManager = collectionManager;

        this.commandRunManager = commandRunManager;
        try {
            start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws IllegalArgumentException, JsonSyntaxException, IOException {
        StringBuilder stringData = fileManager.read();

        PriorityQueue<Person> people = JsonParser.toData(String.valueOf(stringData));
        collectionManager.initialiseData(people);

    }


    public String returnStringResponse(String name,String arg) {
        CommandResult commandResult;
        commandResult = commandRunManager.runCommand(name, arg);
        return commandResult.getOutput();
    }
}
