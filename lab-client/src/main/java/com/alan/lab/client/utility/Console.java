package com.alan.lab.client.utility;

import com.alan.lab.client.commands.CommandResult;
import com.alan.lab.client.data.Person;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Objects;
import java.util.PriorityQueue;


/**
 * The main class for app to be run
 */
public class Console {
    private final CommandRunManager commandRunManager;

    private final OutputManager outputManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final UserInputManager userInputManager;

    public Console(FileManager fileManager, UserInputManager userInputManager,
                   CollectionManager collectionManager, OutputManager outputManager,
                   CommandRunManager commandRunManager) {
        this.fileManager = fileManager;
        this.userInputManager = userInputManager;
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
        this.commandRunManager = commandRunManager;
    }

    public void start() throws IllegalArgumentException, JsonSyntaxException, IOException {
        StringBuilder stringData = fileManager.read();

        PriorityQueue<Person> people = JsonParser.toData(String.valueOf(stringData));
        collectionManager.initialiseData(people);

        startCommandCycle();
    }

    private String readNextCommand() {
        outputManager.print(">>>");
        return userInputManager.nextLine();
    }

    private void startCommandCycle() {
        CommandResult commandResult;
        do {
            String name = "";
            String arg = "";
            String input = readNextCommand();
            String[] commandNameAndArg = new ParseToNameAndArg(input).getAll();
            if (commandNameAndArg.length >= 1) {
                name = commandNameAndArg[0];
            }
            if (commandNameAndArg.length >= 2) {
                arg = commandNameAndArg[1];
            }
            try {
                commandResult = commandRunManager.runCommand(name, arg);
            } catch (Exception e) {
                commandResult = new CommandResult(false, e.getMessage() + " something bad,try ");

            }
            outputManager.println(commandResult.getOutput());
        } while (!Objects.requireNonNull(commandResult).isExit());
    }
}
