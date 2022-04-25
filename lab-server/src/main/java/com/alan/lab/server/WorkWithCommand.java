package com.alan.lab.server;

import com.alan.lab.server.utility.*;

public class WorkWithCommand {
    private final FileManager fileManager;
    private final Console console;

    public WorkWithCommand(String fileName) {
        this.fileManager = new FileManager(fileName);
        CollectionManager collectionManager = new CollectionManager();
        HistoryManager historyManager = new HistoryManager();
        CommandRunManager commandRunManager = new CommandRunManager(new CommandManager(fileManager, collectionManager,historyManager),historyManager);
        console = new Console(fileManager,collectionManager,commandRunManager);
    }
    public String returnStringResponce(String name,String arg) {
        return console.returnStringResponse(name,arg);
    }
}
