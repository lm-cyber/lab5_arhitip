package com.alan.lab.server.utility;


import com.alan.lab.server.commands.*;

import java.util.HashSet;

/**
 * Class for storing commands objects.
 */
public class CommandManager {
    private final HashSet<Command> commands = new HashSet<>();

    public CommandManager(FileManager fileManager,
                          CollectionManager collectionManager,
                          HistoryManager historyManager) {
        commands.add(new HelpCommand());
        commands.add(new ShowCommand(collectionManager));
        commands.add(new RemoveByIdCommand(collectionManager));
        commands.add(new ClearCommand(collectionManager));
        commands.add(new FilterGreaterThanHeightCommand(collectionManager));
        commands.add(new PrintDescendingCommand(collectionManager));
        commands.add(new InfoCommand(collectionManager));
        commands.add(new HistoryCommand(historyManager));
        commands.add(new AverageOfHeightCommand(collectionManager));
        commands.add(new RemoveHeadCommand(collectionManager));


        commands.add(new AddCommand(collectionManager));
        commands.add(new UpdateCommand(collectionManager));
        commands.add(new AddIfMinCommand(collectionManager));

    }

    public HashSet<Command> getCommands() {
        return commands;
    }
}
