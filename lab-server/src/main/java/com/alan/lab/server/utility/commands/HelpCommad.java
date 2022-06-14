package com.alan.lab.server.utility.commands;

import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;

public class HelpCommad extends Command {
    private CollectionManager collectionManager;
    private final String response = "    help : вывести справку по доступным командам\n"
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

    public HelpCommad() {
    }

    @Override
    public Response execute(Object arg) {
        return new Response(response, false);
    }
}
