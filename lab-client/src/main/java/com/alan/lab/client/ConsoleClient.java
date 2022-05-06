package com.alan.lab.client;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Request;
import com.alan.lab.common.network.RequestWithPerson;
import com.alan.lab.common.network.RequestWithPersonType;
import com.alan.lab.common.network.Response;
import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.ParseToNameAndArg;
import com.alan.lab.common.utility.UserInputManager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ConsoleClient {
    private static final int TIMEOUT = 10;
    private static final int MILLIS_IN_SECONDS = 1000;
    private final UserInputManager userInputManager;
    private final OutputManager outputManager;
    private String inputPrefix = "> ";
    private ObjectSocketChannelWrapper remote;
    private InetSocketAddress addr;
    private final Logger logger;

    public ConsoleClient(UserInputManager userInputManager, OutputManager outputManager, InetSocketAddress addr) throws IOException {
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
        this.addr = addr;
        this.logger = Logger.getLogger("log");
        File lf = new File("client.log");
        FileHandler fh = new FileHandler(lf.getAbsolutePath(), true);
        logger.addHandler(fh);
    }


    private boolean exitOrSourceChangeChecker(String input) throws IOException {
        if ("exit".equals(input)) {
            logger.fine("success exit");
            return true;
        }
        if (input.startsWith("execute_script")) {
            userInputManager.connectToFile(new File(input.split(" ", 2)[1]));
            logger.info("change input source");
        }
        return false;
    }

    private boolean responseHandler() throws IOException {

        boolean addCommand = false;
        Response response = waitForResponse();
        if (response != null) {
            outputManager.println(response.getMessage().toString());
            logger.fine("success got response");
            addCommand = response.getAddsCommand();
            if (addCommand) {
                logger.info("response with adding");
            }
        } else {
            logger.severe("request failed");
            outputManager.println("Request failed");
        }
        return addCommand;
    }
    private void sendRequestWithPerson(RequestWithPersonType type) throws IOException {
        RequestWithPerson requestWithPerson;
        Person person = AddElem.add(userInputManager, outputManager);
        requestWithPerson = new RequestWithPerson(person, type);
        logger.info("creating new person");
        remote.sendMessage(requestWithPerson);
        logger.info("send request with person");
    }
    private void sendRequest(ParseToNameAndArg parseToNameAndArg) throws IOException {
        Request request = new Request(parseToNameAndArg.getName(), parseToNameAndArg.getArg());
                        remote.sendMessage(request);
                        logger.info("send request");
    }

    private void inputCycle() {
        Boolean addCommand = false;
        RequestWithPersonType type = null;
        String input = null;
        boolean shouldContinue = true;
        while (shouldContinue) {
            if (!addCommand) {
                input = userInputManager.nextLine();
            }
            try {
                if (exitOrSourceChangeChecker(input)) {
                    return;
                }
                Request request = null;
                RequestWithPerson requestWithPerson = null;
                try {
                    ParseToNameAndArg parseToNameAndArg = new ParseToNameAndArg(input);
                    if (addCommand) {
                        sendRequestWithPerson(type);
                    } else {
                        sendRequest(parseToNameAndArg);
                    }
                    addCommand = responseHandler();
                    if (addCommand) {
                        type = RequestWithPersonType.valueOf(parseToNameAndArg.getName().toUpperCase());
                    }
                } catch (NumberFormatException e) {
                    logger.warning("bad args");
                    outputManager.println("problem with args");
                }
                remote.clearInBuffer();
            } catch (IOException e) {
                logger.severe("IOException with remote");
                outputManager.println("Caught exception when trying to send request");
                return;
            }
        }
    }

    public void run() throws IOException {
        try (SocketChannel socket = SocketChannel.open()) {
            if (!connection(socket)) {
                logger.severe("server dont  response");
                return;
            }
            logger.fine("success connection ");
            socket.configureBlocking(false);
            remote = new ObjectSocketChannelWrapper(socket);

            inputCycle();
        }
    }

    private boolean connection(SocketChannel socket) {
        int second = 0;
        long start = System.currentTimeMillis();
        while (second < TIMEOUT) {
            try {
                socket.connect(addr);
                return true;
            } catch (IOException e) {

                if (System.currentTimeMillis() >= start + (long) (second + 1) * MILLIS_IN_SECONDS) {
                    outputManager.print(".");
                    second++;
                }
            }
        }
        outputManager.println("time out");
        return false;
    }

    private Response waitForResponse() throws IOException {
        int seconds = 0;
        long start = System.currentTimeMillis();
        while (seconds < TIMEOUT) {
            if (remote.checkForMessage()) {
                Object received = remote.getPayload();
                if (received instanceof Response) {
                    return (Response) received;
                } else {
                    outputManager.println("Received invalid response from server");
                    break;
                }
            }
            if (System.currentTimeMillis() >= start + (long) (seconds + 1) * MILLIS_IN_SECONDS) {
                outputManager.print(".");
                seconds++;
            }
        }
        outputManager.println("Timed out after " + TIMEOUT + " seconds.");
        return null;
    }
}
