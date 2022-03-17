package com.alan.lab.client.commands;


import com.alan.lab.client.commands.subcommands.AddElem;
import com.alan.lab.client.data.Person;
import com.alan.lab.client.utility.CollectionManager;
import com.alan.lab.client.utility.OutputManager;
import com.alan.lab.client.utility.UserInputManager;


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
        Integer errCode;
        Person person = AddElem.add(arg);
        if (person != null) {
            errCode = collectionManager.add(person);
            if (errCode == 0) {
                return new CommandResult(false, "The element was added successfully");
            }
            if (errCode == 1) {
                return new CommandResult(false, "not unique id");
            }
            return new CommandResult(false, "not unique passportId");
        }
        return new CommandResult(false, "bat arg");
    }
}
