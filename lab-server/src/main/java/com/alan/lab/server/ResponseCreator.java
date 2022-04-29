package com.alan.lab.server;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.exceptions.NotMinException;
import com.alan.lab.server.utility.CollectionManager;
import com.alan.lab.server.utility.HistoryManager;
import com.alan.lab.common.network.Response;
public class ResponseCreator {
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
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
    }

    public void addHistory(String input) {
        historyManager.addNote(input);
    }

    public Long getLastId() {
        return lastId;
    }

    public Response executeCommand(String name) {
        Response response;
        switch (name) {
            case "help":
                response = new Response(helpString, false);
                break;
            case "show":
                response = new Response(collectionManager.toString(), false);
                break;
            case "info":
                response = new Response("type:" + collectionManager.getType().toString()
                        + "\ndate:" + collectionManager.getCreationDate().toString() + "\n"
                        + "count_elem:" + collectionManager.getSize() + "\n", false);
                break;
            case "remove_head":
                if (collectionManager.isEmpty()) {
                    response = new Response("is empty ,cant remove_head", false);
                    break;
                }
                response = new Response(collectionManager.poll().toString(), false);
                break;
            default:
                response = new Response("This command was not found. Please use \"help\" to know about available commands", false);
                break;

        }
        return response;


    }

    @SuppressWarnings("methodlength")
    public Response executeCommand(String name, Object arg) {
        Response response;
        switch (name) {
            case "remove_by_id":
                if (!collectionManager.removeByID((Long) arg)) {
                    response = new Response("delete success", false);

                }
                response = new Response("havent id", false);
                break;
            case "update":
                if (collectionManager.isHaveId((Long) arg)) {
                    lastId = (Long) arg;
                    response = new Response("have", true);
                    break;
                }
                response = new Response("havent", false);
                break;
            case "add":
                response = new Response("add start", true);
                break;
            case "clear":
                collectionManager.clear();
                response = new Response("clear success", false);
                break;
            case "remove_head":
                if (collectionManager.isEmpty()) {
                    response = new Response("is empty ,cant remove_head", false);
                    break;
                }
                response = new Response(collectionManager.poll().toString(), false);
                break;
            case "add_if_min":
                response = new Response("add if min start", true);
                break;
            case "average_of_height":
                response = new Response(collectionManager.averageHeight().toString(), false);
                break;
            case "filter_greater_than_height":
                response = new Response(collectionManager.filterGreaterThanHeight((Float) arg).toString(), false);
                break;
            case "print_descending":
                response = new Response(collectionManager.descending().toString(), false);
                break;
            case "history":
                response = new Response(historyManager.niceToString(), false);
                break;
            default:
                response = executeCommand(name);
                break;
        }
        return response;
    }

    public Response add(Person person) {
        if (collectionManager.add(person)) {
            return new Response("add success", false);
        }
        return new Response("contains passport", false);

    }

    public Response update(Person person) {
        person.setId(lastId);
        if (collectionManager.update(person)) {
            return new Response("add success", false);
        }
        return new Response("contains passport", false);
    }

    public Response addIfMin(Person person) {
        try {
            if (collectionManager.addMin(person)) {
                return new Response("add success", false);
            }
            return new Response("passport contains", false);
        } catch (NotMinException e) {
            return new Response(e.getMessage(), false);


        }
    }

}
