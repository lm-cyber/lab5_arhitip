package com.alan.lab.client.commands;

import com.alan.lab.client.commands.subcommands.AddElem;
import com.alan.lab.client.data.Person;
import com.alan.lab.client.utility.CollectionManager;
import com.alan.lab.client.utility.OutputManager;
import com.alan.lab.client.utility.UserInputManager;

import java.util.Optional;


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
        String[] lines = arg.split(" ", 2);
        Optional<Float> optionHeight = collectionManager.getMainData().stream().map(Person::getHeight).min(Float::compare);
        if (optionHeight.orElse(0F) < Float.parseFloat(lines[0])) {
            Person person = AddElem.add(arg);
            if (person != null) {
                Integer errcode = collectionManager.add(person);
                if(errcode == 0) {
                    return new CommandResult(false, "The element was added successfully");
                }
                if (errcode == 1) {
                    return new CommandResult(false, "not unique id");
                }
                return  new CommandResult(false, "not unique passwordId");
            }
        }
        return new CommandResult(false, "The element was not min, so it was not added");
    }
}
