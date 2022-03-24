package com.alan.lab.client.commands;


import com.alan.lab.client.commands.subcommands.AddElem;
import com.alan.lab.client.data.Person;
import com.alan.lab.client.exceptions.InvalidEmptyLineException;
import com.alan.lab.client.exceptions.InvalidPassportIDSizeException;
import com.alan.lab.client.exceptions.InvalidValuesException;
import com.alan.lab.client.exceptions.PasswordIDContainsException;
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
        Person person;
        try {
            person = AddElem.add(true, userInputManager, outputManager, collectionManager);
            collectionManager.add(person);
            return new CommandResult(false, "success added");
        } catch (InvalidValuesException | PasswordIDContainsException | InvalidPassportIDSizeException | InvalidEmptyLineException e) {
            return new CommandResult(false, "not success:" + e.getMessage());
        }

    }
}
