package com.alan.lab.client;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Request;
import com.alan.lab.common.network.RequestWithPerson;
import com.alan.lab.common.network.RequestWithPersonType;
import com.alan.lab.common.network.Response;
import com.alan.lab.common.users.AuthCredentials;
import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.ParseToNameAndArg;
import com.alan.lab.common.utility.UserInputManager;
import com.alan.lab.common.utility.nonstandardcommand.NonStandardCommand;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.IllegalBlockingModeException;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ConsoleClient {
    private static final int TIMEOUT = 10;
    private static final int TIMEOUTMS = 100;
    private static final int MILLIS_IN_SECONDS = 1000;
    private final UserInputManager userInputManager;
    private final OutputManager outputManager;
    private ObjectSocketChannelWrapper remote;
    private InetSocketAddress addr;
    private final Logger logger;
    private final NonStandardCommand nonStandardCommandClient;
    private RequestCreator requestCreator;
    public ConsoleClient(UserInputManager userInputManager, OutputManager outputManager, InetSocketAddress addr) throws IOException {
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
        this.addr = addr;
        this.logger = Logger.getLogger("log");
        this.nonStandardCommandClient = new NonStandardCommandClient(userInputManager, logger);
        File lf = new File("client.log");
        FileHandler fh = new FileHandler(lf.getAbsolutePath(), true);
        logger.addHandler(fh);

    }



    private void responseFormating(Collection list) {
        int count = 0;
        for (Object person : list) {
            outputManager.println("___________________________________________________________");
            outputManager.println(person.toString());
            outputManager.println(Integer.toString(count) + " " + Integer.toString(count) + " " + Integer.toString(count));
            count++;
        }

    }

    private boolean responseHandler() throws IOException {

        boolean addCommand = false;
        Response response = waitForResponse();
        if (response != null) {
            if (response.getMessage() instanceof Collection) {
                responseFormating((Collection) response.getMessage());
            } else {
                outputManager.println(response.getMessage().toString());
            }
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
        RequestWithPersonType type = null;
        boolean addCommand = false;
        String input = "";
        while (input != null) {
            if (!addCommand) {
                input = userInputManager.nextLine();
            }
            try {
                if (nonStandardCommandClient.execute(input)) {
                    continue;
                }
                try {
                    requestCreator.requestCreate(input, addCommand);
                    addCommand = responseHandler();
                } catch (NumberFormatException e) {
                    logger.warning("bad args");
                    outputManager.println("problem with args");
                }
                remote.clearInBuffer();
            } catch (IOException e) {
                if (!waitForReconnection()) {
                    logger.severe("IOException with remote");
                    outputManager.println("Caught exception when trying to send request");
                    return;
                }
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
            this.requestCreator = new RequestCreator(remote, userInputManager, outputManager);

            inputCycle();
        }
    }

    public AuthCredentials changeUser() {
        outputManager.print("login :");
        String login = userInputManager.nextLine();
        outputManager.print("password :");
        String password = userInputManager.nextLine();
        return new AuthCredentials(login, password);
    }


    private boolean connection(SocketChannel socket) {
        int second = 0;
        long start = System.currentTimeMillis();
        while (second < TIMEOUT) {
            try {
                socket.socket().connect(addr, TIMEOUTMS);
                return true;
            } catch (IOException | IllegalBlockingModeException e) {

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
     private boolean waitForReconnection() {
         outputManager.println("starting to reconnection");
         try {
             run();
             return true;
         } catch (IOException e) {
             return false;
         }
     }
}
