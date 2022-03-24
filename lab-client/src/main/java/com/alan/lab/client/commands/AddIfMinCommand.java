package com.alan.lab.client.commands;

import com.alan.lab.client.commands.subcommands.AddElem;
import com.alan.lab.client.data.Person;
import com.alan.lab.client.exceptions.InvalidEmptyLineException;
import com.alan.lab.client.exceptions.InvalidPassportIDSizeException;
import com.alan.lab.client.exceptions.InvalidValuesException;
import com.alan.lab.client.exceptions.PasswordIDContainsException;
import com.alan.lab.client.exceptions.NotMinException;
import com.alan.lab.client.utility.CollectionManager;
import com.alan.lab.client.utility.OutputManager;
import com.alan.lab.client.utility.UserInputManager;



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
        } catch (InvalidValuesException | PasswordIDContainsException | InvalidPassportIDSizeException | InvalidEmptyLineException | NotMinException e) {
            return new CommandResult(false, "not success:" + e.getMessage());
        }

    }
}
