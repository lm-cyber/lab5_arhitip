package com.alan.lab.client.commands;

import com.alan.lab.client.utility.UserInputManager;

public class ExitCommand extends Command {
    private UserInputManager userInputManager;

    public ExitCommand(UserInputManager userInputManager) {
        super("exit");
        this.userInputManager = userInputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        userInputManager.close();
        return new CommandResult(true, "Exiting...");
    }
}
