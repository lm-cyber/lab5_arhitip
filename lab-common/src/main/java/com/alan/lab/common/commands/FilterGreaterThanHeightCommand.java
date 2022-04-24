package com.alan.lab.common.commands;



import com.alan.lab.common.utility.CollectionManager;


public class FilterGreaterThanHeightCommand extends Command {

    private final CollectionManager collectionManager;

    public FilterGreaterThanHeightCommand(CollectionManager collectionManager) {
        super("filter_greater_than_height");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        try {
            return new CommandResult(false, collectionManager.filterGreaterThanHeight(Float.parseFloat(arg)).toString());
        } catch (NumberFormatException e) {
            return new CommandResult(false, e.getMessage());
        }
    }
}
