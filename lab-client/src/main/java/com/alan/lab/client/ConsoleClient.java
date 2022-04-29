package com.alan.lab.client;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Request;
import com.alan.lab.common.network.RequestWithPerson;
import com.alan.lab.common.network.RequestWithPersonType;
import com.alan.lab.common.network.Response;
import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.ParseToNameAndArg;
import com.alan.lab.common.utility.TerminalColors;
import com.alan.lab.common.utility.UserInputManager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ConsoleClient {
    private static final int TIMEOUT = 10;
    private static final int MILLIS_IN_SECONDS = 1000;
    private final UserInputManager userInputManager;
    private final OutputManager outputManager;
    private String inputPrefix = "> ";
    private ObjectSocketChannelWrapper remote;
    private InetSocketAddress addr;

    public ConsoleClient(UserInputManager userInputManager, OutputManager outputManager, InetSocketAddress addr) throws IOException {
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
        this.addr = addr;
    }

    private void writeTrace(Exception e) {
        Throwable t = e;

        while (t != null) {
            outputManager.println(TerminalColors.colorString(t.toString(), TerminalColors.RED));
            t = t.getCause();
        }

        outputManager.println("Use "
                + TerminalColors.colorString("help [command name]", TerminalColors.GREEN)
                + " to get more information on usage of commands"
        );
    }

    private boolean chekInput(String input) throws IOException {
        if ("exit".equals(input)) {
            return true;
        }
        if (input.startsWith("execute_script")) {
            userInputManager.connectToFile(new File(input.split(" ", 2)[1]));
        }
        return false;
    }

    @SuppressWarnings("methodlength")
    private void inputCycle() {
        boolean addCammand = false;
        RequestWithPersonType type = null;
        String input = null;
        boolean shouldContinue = true;
        while (shouldContinue) {
            if (!addCammand) {
                input = userInputManager.nextLine();
                if (input == null) {
                    shouldContinue = false;
                }
            }
            try {
                if (chekInput(input)) {
                    return;
                }
                Request request = null;
                RequestWithPerson requestWithPerson = null;
                try {
                    ParseToNameAndArg parseToNameAndArg = new ParseToNameAndArg(input);
                    if (addCammand) {
                        Person person = AddElem.add(userInputManager, outputManager);
                        requestWithPerson = new RequestWithPerson(person, type);
                    } else {
                        request = new Request(parseToNameAndArg.getName(), parseToNameAndArg.getArg());
                    }
                    if (request != null) {
                        remote.sendMessage(request);
                    } else {
                        remote.sendMessage(requestWithPerson);
                    }
                    Response response = waitForResponse();
                    if (response != null) {
                        outputManager.println(response.getMessage());
                        addCammand = response.getAddsCommand();
                        if (addCammand) {
                            type = RequestWithPersonType.valueOf(parseToNameAndArg.getName().toUpperCase());
                        }

                    } else {
                        outputManager.println("Request failed");
                    }
                } catch (NumberFormatException e) {
                    outputManager.println("problem with args");
                }
                remote.clearInBuffer();
            } catch (IOException e) {
                outputManager.println("Caught exception when trying to send request");
                return;
            }
        }
    }

    public void run() throws IOException {
        try (SocketChannel socket = SocketChannel.open()) {
            if (!connection(socket)) {
                return;
            }
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
