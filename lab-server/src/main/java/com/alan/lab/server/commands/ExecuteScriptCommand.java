package com.alan.lab.server.commands;


import com.alan.lab.common.exceptions.DoubleExecuteException;
import com.alan.lab.common.utility.UserInputManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExecuteScriptCommand extends Command {
    private List<String> args;
    private final UserInputManager userInputManager;

    public ExecuteScriptCommand(UserInputManager userInputManager) {
        super("execute_script");
        this.userInputManager = userInputManager;
        args = new ArrayList<>();
    }

    @Override
    public CommandResult execute(String arg) {
        try {
            if (!userInputManager.getChekReg()) {
                args.clear();
            }
            if (args.contains(arg)) {
                return new CommandResult(false, "rekursiv");
            }
            args.add(arg);
            userInputManager.connectToFile(new File(arg));
            return new CommandResult(false, "Starting to execute script...");
        } catch (IOException e) {
            return new CommandResult(false, "There was a problem opening the file. Check if it is available and you have written it in the command arg correctly.");
        } catch (DoubleExecuteException e) {
            return new CommandResult(false, e.getMessage());
        }
    }
}
