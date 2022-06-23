package com.alan.lab.client;

import com.alan.lab.common.network.RequestWithPersonType;
import com.alan.lab.common.network.Response;
import com.alan.lab.common.users.AuthCredentials;
import com.alan.lab.common.utility.OutputManager;
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

    private boolean[] responseHandler() throws IOException {

        boolean[] addCommandAndAuth = new boolean[2];
        Response response = waitForResponse();
        if (response != null) {
            if (response.getMessage() instanceof Collection) {
                responseFormating((Collection) response.getMessage());
            } else {
                outputManager.println(response.getMessage().toString());
            }
            logger.fine("success got response");
            addCommandAndAuth[0] = response.getAddsCommand();
            addCommandAndAuth[1] = response.getAuthBoolean();
            if (addCommandAndAuth[0]) {
                logger.info("response with adding");
            }
        } else {
            logger.severe("request failed");
            outputManager.println("Request failed");
        }
        return addCommandAndAuth;
    }

    private void inputCycle() {
        RequestWithPersonType type = null;
        boolean[] addCommandAndAuth = new boolean[2];
        AuthCredentials authCredentials = null;
        String input = "";
        while (input != null) {
            try {
                if (!addCommandAndAuth[1]) {
                    authCredentials = connectAuth();
                    addCommandAndAuth[1] = true;
                }
                if (!addCommandAndAuth[0]) {
                    input = userInputManager.nextLine();
                }
                if (nonStandardCommandClient.execute(input)) {
                    continue;
                }
                try {
                    if ("log".equals(input) || "reg".equals(input)) {
                        authCredentials = connectAuth(input);
                        addCommandAndAuth[1] = true;
                        continue;
                    }
                    requestCreator.requestCreate(input, addCommandAndAuth, authCredentials);
                    addCommandAndAuth = responseHandler();
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

    private AuthCredentials connectAuth() throws IOException {
        boolean auth = false;
        AuthCredentials authCredentials = null;
        while (!auth) {
            try {
                authCredentials = choseAuth();
                requestCreator.auth(authCredentials);
                auth = responseHandler()[1];
            } catch (NumberFormatException e) {
                outputManager.println("problem with auth");
            }
            remote.clearInBuffer();
        }
        return authCredentials;
    }
    private AuthCredentials connectAuth(String input) throws IOException {
        boolean auth = false;
        AuthCredentials authCredentials = null;
        while (!auth) {
            try {
                authCredentials = choseAuth(input);
                requestCreator.auth(authCredentials);
                auth = responseHandler()[1];
            } catch (NumberFormatException e) {
                outputManager.println("problem with auth");
            }
            remote.clearInBuffer();
        }
        return authCredentials;
    }

    private AuthCredentials changeUser() {
        outputManager.print("login :");
        String login = userInputManager.nextLine();
        outputManager.print("password :");
        String password = userInputManager.nextLine();
        return new AuthCredentials(login, password);
    }


    private AuthCredentials choseAuth() {
        boolean shouldContinue = true;
        String input;
        while (shouldContinue) {
            outputManager.println("reg or log");
            input = userInputManager.nextLine();
            if ("reg".equals(input)) {
                AuthCredentials credentials = changeUser();
                credentials.setNewUser(true);
                return credentials;
            }
            if ("log".equals(input)) {
                return changeUser();
            }
        }
        return null;
    }
    private AuthCredentials choseAuth(String input) {
        if ("reg".equals(input)) {
            AuthCredentials credentials = changeUser();
            credentials.setNewUser(true);
            return credentials;
        }
        if ("log".equals(input)) {
            return changeUser();
        }
        return null;
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
