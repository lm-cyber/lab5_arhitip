package com.alan.lab.client;

import com.alan.lab.common.utility.CollectionManager;
import com.alan.lab.common.utility.CommandManager;
import com.alan.lab.common.utility.Console;
import com.alan.lab.common.utility.FileManager;
import com.alan.lab.common.utility.CommandRunManager;
import com.alan.lab.common.utility.HistoryManager;
import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.UserInputManager;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.NoSuchElementException;

public final class ClientOld {
    private ClientOld() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {

        final OutputManager outputManager = new OutputManager();

        if (args.length == 0) {
            outputManager.println("This program needs a file in argument to work with.");
            return;
        }

        if (!args[0].endsWith(".json")) {
            outputManager.println("This program can only work with .json file.");
            return;
        }
        try (UserInputManager userInputManager = new UserInputManager()) {

            final HistoryManager historyManager = new HistoryManager();
            final CollectionManager collectionManager = new CollectionManager();
            final FileManager fileManager = new FileManager(args[0]);
            final CommandManager commandManager = new CommandManager(fileManager, userInputManager, collectionManager, outputManager, historyManager);
            final CommandRunManager commandRunManager = new CommandRunManager(commandManager, historyManager);
            final Console console = new Console(fileManager,
                    userInputManager, collectionManager, outputManager,
                    commandRunManager);
            try {
                console.start();
            } catch (IOException e) {
                outputManager.println("Could not read the file. Check if it is available.");
            } catch (JsonSyntaxException | IllegalArgumentException e) {
                outputManager.println("The file does not keep data in correct format.");
            } catch (NoSuchElementException e) {
                outputManager.println("EOF");
            }
        }
    }
}
