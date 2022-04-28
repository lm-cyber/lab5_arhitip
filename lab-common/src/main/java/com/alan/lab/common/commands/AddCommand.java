package com.alan.lab.common.commands;


import com.alan.lab.common.commands.subcommands.AddElem;
import com.alan.lab.common.data.Person;
import com.alan.lab.common.utility.CollectionManager;
import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.UserInputManager;


public class AddCommand extends Command {
    private final UserInputManager userInputManager;
    private final OutputManager outputManager;
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("add");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        Person person;
        person = AddElem.add( userInputManager, outputManager);
        collectionManager.add(person);
        return new CommandResult(false, "success added");

    }
}
