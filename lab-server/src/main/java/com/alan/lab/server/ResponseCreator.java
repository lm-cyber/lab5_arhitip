package com.alan.lab.server;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.RequestWithPersonType;
import com.alan.lab.common.network.Response;
import com.alan.lab.common.users.AuthCredentials;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.HistoryManager;
import com.alan.lab.server.utility.commands.*;

import java.util.HashMap;

public class ResponseCreator {
    private final HistoryManager historyManager;
    private final CollectionManager collectionManager;
    private final HashMap<String, Command> commands;
    private final HashMap<RequestWithPersonType, CommandWithPerson> typeCommand;
    private Long lastId;
    private final SqlUserManager sqlUserManager;

    public ResponseCreator(HistoryManager historyManager, CollectionManager collectionManager, SqlUserManager sqlUserManager) {
        this.historyManager = historyManager;
        this.collectionManager = collectionManager;
        this.commands = new HashMap<>();
        this.typeCommand = new HashMap<>();
        this.sqlUserManager = sqlUserManager;

        typeCommand.put(RequestWithPersonType.ADD, new AddCommand(collectionManager, sqlUserManager));
        typeCommand.put(RequestWithPersonType.UPDATE, new UpdateCommand(collectionManager, sqlUserManager));
        typeCommand.put(RequestWithPersonType.ADD_IF_MIN, new AddIfMinCommand(collectionManager, sqlUserManager));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("help", new HelpCommad());
        commands.put("history", new HistoryCommand(historyManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("info", new InfoCommand(collectionManager));
        commands.put("average_of_height", new AverageOfHeightCommand(collectionManager));
        commands.put("filter_greater_than_height", new FilterGreaterThanHeightCommand(collectionManager));
        commands.put("print_descending", new PrintDescendingCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        commands.put("remove_head", new RemoveHeadCommand(collectionManager));
        commands.put("add", new AddCom());
        commands.put("update", new UpdateCom(collectionManager));
        commands.put("add_if_min", new AddCom());
        commands.put("reg",new RegisterCommand(sqlUserManager));
        commands.put("log",new LoginCommand(sqlUserManager));
    }

    public void addHistory(String input) {
        historyManager.addNote(input);
    }



    public Response executeCommand(String name, Object arg, AuthCredentials authCredentials) {

        Long userID = sqlUserManager.authenticate(authCredentials);
        if(userID == null && !"reg".equals(name)) {
            return new Response("not auth", false, false);
        }
        if (arg instanceof Long) {
            lastId = (Long) arg;
        }
        return commands.getOrDefault(name, new NameHaventCommand()).execute(arg,userID);
    }


    public Response executeCommandWithPerson(RequestWithPersonType type, Person person, AuthCredentials authCredentials) {
        return typeCommand.get(type).execute(person, lastId, sqlUserManager.authenticate(authCredentials));
    }
}
