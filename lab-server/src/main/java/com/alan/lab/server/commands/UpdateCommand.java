package com.alan.lab.server.commands;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.utility.CollectionManager;
import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.UserInputManager;
import com.alan.lab.server.commands.subcommands.AddElem;

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
        if (!collectionManager.isHaveId(id)) {
            return new CommandResult(false, "have not this id");
        }
        Person person;
        person = AddElem.add(false, userInputManager, outputManager, collectionManager);
        person.setId(id);
        collectionManager.removeByID(id);
        collectionManager.add(person);
        return new CommandResult(false, "success added");

    }

}
