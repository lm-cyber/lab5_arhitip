package com.alan.lab.client.commands;

import com.alan.lab.client.data.Person;
import com.alan.lab.client.utility.CollectionManager;
import com.alan.lab.client.utility.OutputManager;
import com.alan.lab.client.utility.UserInputManager;

import java.util.Objects;
import java.util.Optional;

public class UpdateCommand extends Command {
    private static final int K3 = 3;
    private static final int K4 = 4;
    private static final int K5 = 5;
    private static final int K6 = 6;
    private static final int K7 = 7;
    private static final int K8 = 8;
    private static final int K9 = 9;
    private static final int K10 = 10;


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
        String[] lines = arg.split(" ", 2);
        Long argLong;
        try {
            argLong = Long.parseLong(lines[0]);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Your argument was incorrect. The command was not executed.");
        }
        Optional<Person> any = collectionManager.getMainData().stream().filter(person -> Objects.equals(person.getId(), argLong)).findAny();
        if (any.isPresent()) {
            any.get().update();
            return new CommandResult(false, "The element was updated successfully");
        }
        return new CommandResult(false, "Written id was not found. The command was not executed");
    }

}
