package com.alan.lab.server;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;
import com.alan.lab.server.utility.HistoryManager;
import com.alan.lab.server.utility.commands.*;

import java.util.HashMap;

public class ResponseCreator {
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
    private final HashMap<String,Command> commands;
    private Long lastId;
    private final String helpString = "    help : вывести справку по доступным командам\n"
            + "    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n"
            + "    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"
            + "    add {element} : добавить новый элемент в коллекцию\n"
            + "    update id {element} : обновить значение элемента коллекции, id которого равен заданному\n"
            + "    remove_by_id id : удалить элемент из коллекции по его id\n"
            + "    clear : очистить коллекцию\n"
            + "    execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n"
            + "    exit : завершить программу (без сохранения в файл)\n"
            + "    remove_head : вывести первый элемент коллекции и удалить его\n"
            + "    add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n"
            + "    history : вывести последние 13 команд (без их аргументов)\n"
            + "    average_of_height : вывести среднее значение поля height для всех элементов коллекции\n"
            + "    filter_greater_than_height height : вывести элементы, значение поля height которых больше заданного\n"
            + "    print_descending : вывести элементы коллекции в порядке убывания";

    public ResponseCreator(HistoryManager historyManager, CollectionManager collectionManager) {
        this.historyManager = historyManager;
        this.collectionManager = collectionManager;
        this.commands = new HashMap<>();
        commands.put("clear",new ClearCommand(collectionManager));
        commands.put("help",new HelpCommad());
        commands.put("history",new HistoryCommand(historyManager));
        commands.put("show",new ShowCommand(collectionManager));
        commands.put("info",new InfoCommand(collectionManager));
        commands.put("average_of_height",new AverageOfHeightCommand(collectionManager));
        commands.put("filter_greater_than_height",new FilterGreaterThanHeightCommand(collectionManager));
        commands.put("print_descending",new PrintDescendingCommand(collectionManager));
        commands.put("remove_by_id",new RemoveByIdCommand(collectionManager));
        commands.put("remove_head",new RemoveHeadCommand(collectionManager));
    }

    public void addHistory(String input) {
        historyManager.addNote(input);
    }

    public Long getLastId() {
        return lastId;
    }


    public Response executeCommand(String name, Object arg) {
        return commands.getOrDefault(name,new NameHaventCommand()).execute(arg);
    }


    public Response executeCommandWithPerson(String name , Person person) {
        return null;
    }
}
