package com.alan.lab.client.commands;

import com.alan.lab.client.data.Person;
import com.alan.lab.client.utility.CollectionManager;

import java.util.OptionalDouble;

public class AverageOfHeightCommand extends Command {
    private CollectionManager collectionManager;

    public AverageOfHeightCommand(CollectionManager collectionManager) {
        super("average_of_height");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        OptionalDouble average = collectionManager.getMainData().stream()
                .map(Person::getHeight)
                .mapToDouble(value -> value)
                .average();
        Double d = average.orElse(0D);
        return new CommandResult(false, d.toString());
    }
}
