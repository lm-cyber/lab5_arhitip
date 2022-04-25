package com.alan.lab.server;

import com.alan.lab.server.utility.CollectionManager;
import com.alan.lab.server.utility.HistoryManager;

public class ResponseCreator {
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
    private final String helpString = "    help : вывести справку по доступным командам\n"
            + "    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n"
            + "    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n"
            + "    add {element} : добавить новый элемент в коллекцию\n"
            + "    update id {element} : обновить значение элемента коллекции, id которого равен заданному\n"
            + "    remove_by_id id : удалить элемент из коллекции по его id\n" + "    clear : очистить коллекцию\n"
            + "    save : сохранить коллекцию в файл\n"
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
    }
    public void addHistory(String input) {
        historyManager.addNote(input);
    }
    public String executeCommand(String name, String args) {
        switch (name) {
            case "help" :
                return helpString;
            case "show" :
                return collectionManager.toString();
            case "info" :
                return "type:" + collectionManager.getType().toString()
                        + "\ndate:" + collectionManager.getCreationDate().toString() + "\n"
                        + "count_elem:" + collectionManager.getSize() + "\n";
            default:
                return "This command was not found. Please use \"help\" to know about available commands";
        }
    }

}
