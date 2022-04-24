package com.alan.lab.common.utility;


import com.alan.lab.common.commands.UpdateCommand;
import com.alan.lab.common.commands.RemoveByIdCommand;
import com.alan.lab.common.commands.AddCommand;
import com.alan.lab.common.commands.AddIfMinCommand;
import com.alan.lab.common.commands.ClearCommand;
import com.alan.lab.common.commands.ExecuteScriptCommand;
import com.alan.lab.common.commands.ExitCommand;
import com.alan.lab.common.commands.FilterGreaterThanHeightCommand;
import com.alan.lab.common.commands.HelpCommand;
import com.alan.lab.common.commands.HistoryCommand;
import com.alan.lab.common.commands.InfoCommand;
import com.alan.lab.common.commands.PrintDescendingCommand;
import com.alan.lab.common.commands.SaveCommand;
import com.alan.lab.common.commands.ShowCommand;
import com.alan.lab.common.commands.Command;
import com.alan.lab.common.commands.AverageOfHeightCommand;
import com.alan.lab.common.commands.RemoveHeadCommand;

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
        commands.add(new ExitCommand(userInputManager));
        commands.add(new HistoryCommand(historyManager));
        commands.add(new AverageOfHeightCommand(collectionManager));
        commands.add(new RemoveHeadCommand(collectionManager, outputManager));
    }

    public HashSet<Command> getCommands() {
        return commands;
    }
}
