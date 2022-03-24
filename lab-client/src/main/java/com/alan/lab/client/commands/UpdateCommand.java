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

public class UpdateCommand extends Command {
    private final OutputManager outputManager;
    private final UserInputManager userInputManager;
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("update");
        this.userInputManager = userInputManager;
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    @Override
    public CommandResult execute(String arg) {
            Long id;
        try {
            id = Long.parseLong(arg);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Your argument was incorrect. The command was not executed.");
        }
        if(!collectionManager.isHaveId(id)) {
            return new CommandResult(false, "have not this id");
        }
        Person person;
        try {
            person = AddElem.add(false, userInputManager, outputManager, collectionManager);
            person.setId(id);
            collectionManager.removeByID(id);
            collectionManager.add(person);
            return new CommandResult(false, "success added");
        } catch (InvalidValuesException | PasswordIDContainsException | InvalidPassportIDSizeException | InvalidEmptyLineException e) {
            return new CommandResult(false, "not success:" + e.getMessage());
        }

    }

}
