package com.alan.lab.server.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.exceptions.NotMinException;
import com.alan.lab.common.utility.CollectionManager;
import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.UserInputManager;
import com.alan.lab.server.commands.subcommands.AddElem;


public class AddIfMinCommand extends Command {
    private final OutputManager outputManager;
    private final CollectionManager collectionManager;
    private final UserInputManager userInputManager;

    public AddIfMinCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("add_if_min");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        Person person;
        try {
            person = AddElem.add(true, userInputManager, outputManager, collectionManager);
            collectionManager.addMin(person);
            return new CommandResult(false, "success added");
        } catch (NotMinException e) {
            return new CommandResult(false, "not success:" + e.getMessage());
        }

    }
}
