package com.alan.lab.client.utility;


import com.alan.lab.client.commands.UpdateCommand;
import com.alan.lab.client.commands.RemoveByIdCommand;
import com.alan.lab.client.commands.AddCommand;
import com.alan.lab.client.commands.AddIfMinCommand;
import com.alan.lab.client.commands.ClearCommand;
import com.alan.lab.client.commands.ExecuteScriptCommand;
import com.alan.lab.client.commands.ExitCommand;
import com.alan.lab.client.commands.FilterGreaterThanHeightCommand;
import com.alan.lab.client.commands.HelpCommand;
import com.alan.lab.client.commands.HistoryCommand;
import com.alan.lab.client.commands.InfoCommand;
import com.alan.lab.client.commands.PrintDescendingCommand;
import com.alan.lab.client.commands.SaveCommand;
import com.alan.lab.client.commands.ShowCommand;
import com.alan.lab.client.commands.Command;
import com.alan.lab.client.commands.AverageOfHeightCommand;
import com.alan.lab.client.commands.RemoveHeadCommand;

import java.util.HashSet;

/**
 * Class for storing commands objects.
 */
public class CommandManager {
    private final HashSet<Command> commands = new HashSet<>();

    public CommandManager(FileManager fileManager, UserInputManager userInputManager,
                          CollectionManager collectionManager, OutputManager outputManager,
                          HistoryManager historyManager) {
        commands.add(new HelpCommand());
        commands.add(new AddCommand(collectionManager, userInputManager, outputManager));
        commands.add(new SaveCommand(fileManager, collectionManager));
        commands.add(new ShowCommand(collectionManager));
        commands.add(new UpdateCommand(collectionManager, userInputManager, outputManager));
        commands.add(new RemoveByIdCommand(collectionManager));
        commands.add(new ClearCommand(collectionManager));
        commands.add(new ExecuteScriptCommand(userInputManager));
        commands.add(new AddIfMinCommand(collectionManager, userInputManager, outputManager));
        commands.add(new FilterGreaterThanHeightCommand(collectionManager));
        commands.add(new PrintDescendingCommand(collectionManager));
        commands.add(new InfoCommand(collectionManager));
        commands.add(new ExitCommand());
        commands.add(new HistoryCommand(historyManager));
        commands.add(new AverageOfHeightCommand(collectionManager));
        commands.add(new RemoveHeadCommand(collectionManager));
    }

    public HashSet<Command> getCommands() {
        return commands;
    }
}
