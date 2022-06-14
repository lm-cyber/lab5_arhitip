package com.alan.lab.client;

import com.alan.lab.common.utility.UserInputManager;
import com.alan.lab.common.utility.nonstandardcommand.NonStandardCommand;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public class NonStandardCommandClient implements NonStandardCommand {
    private final UserInputManager userInputManager;
    private final Logger logger;

    public NonStandardCommandClient(UserInputManager userInputManager, Logger logger) {
        this.userInputManager = userInputManager;
        this.logger = logger;
    }

    @Override
    public boolean execute(String input) throws IOException {
        if ("exit".equals(input)) {
            logger.fine("success exit");
            System.exit(0);
            return false;
        }
        if (input.startsWith("execute_script")) {
            try {
                if (input.split(" ", 2).length == 2) {
                    userInputManager.changeSource(input.split(" ", 2)[1]);
                }
                return true;

            } catch (FileNotFoundException e) {
                logger.severe("cant connect");
            }
            logger.info("change input source");
        }
        return false;
    }
}
