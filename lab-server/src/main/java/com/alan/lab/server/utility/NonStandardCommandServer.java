package com.alan.lab.server.utility;

import com.alan.lab.common.utility.nonstandardcommand.NonStandardCommand;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class NonStandardCommandServer implements NonStandardCommand {
    private final CollectionManager collectionManager;
    private final Logger logger;
    private final BufferedReader in;
    private final FileManager fileManager;

    public NonStandardCommandServer(CollectionManager collectionManager, Logger logger, FileManager fileManager) {
        this.collectionManager = collectionManager;
        this.logger = logger;
        this.in = new BufferedReader(new InputStreamReader(System.in));
        this.fileManager = fileManager;
    }

    @Override
    public boolean execute(String input) throws IOException {
        if (System.in.available() > 0) {
            String command = in.readLine();
            switch (command) {
                case "save":
                    logger.info("save");
                    fileManager.write(JsonParser.toJson(collectionManager.getMainData()));
                    logger.fine("save success");
                    break;
                case "exit":
                    System.out.println("Shutting down");
                    logger.fine("exit");
                    System.exit(0);
                    return true;
                default:
                    System.out.println("Unknown command. Available commands are: save, exit");
            }
        }
        return false;
    }
}
