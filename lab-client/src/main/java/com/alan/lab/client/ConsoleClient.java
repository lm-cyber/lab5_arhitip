package com.alan.lab.client;

import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.*;
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

    private Response waitForResponse() throws IOException {
        int seconds = 0;
        long start = System.currentTimeMillis();

        while (seconds < TIMEOUT) {
            if (remote.checkForMessage()) {
                Object received = remote.getPayload();

                if (received != null && received instanceof Response) {
                    return (Response) received;
                } else {
                    outputManager.println("Received invalid response from server");
                    break;
                }
            }

            if (System.currentTimeMillis() >= start + (seconds + 1) * MILLIS_IN_SECONDS) {
                outputManager.print(".");
                seconds++;
            }
        }

        outputManager.println("Timed out after " + TIMEOUT + " seconds.");
        return null;
    }



    private void inputCycle() {
        boolean addCammand = false;
        RequestWithPersonType type =null;
        String input=null;
        boolean shouldContinue =true;
        while (shouldContinue) {
            if(!addCammand) {
                if ((input = userInputManager.nextLine()) == null) {
                    shouldContinue = false;
                }
            }
            try {

                if (input.equals("exit")) {
                    return;
                }
                if (input.startsWith("execute_script")) {
                    userInputManager.connectToFile(new File(input.split(" ", 2)[1]));
                }
                Request request = null;
                RequestWithPerson requestWithPerson = null;
                try {
                    ParseToNameAndArg parseToNameAndArg = new ParseToNameAndArg(input);
                    if (addCammand) {
                        Person person = AddElem.add(userInputManager, outputManager);
                        requestWithPerson = new RequestWithPerson(person,type);
                    } else {

                        request = new Request(parseToNameAndArg.getName(), parseToNameAndArg.getArg());


                    }


                    // If the command is not only client-side
                    if (request != null) {
                        remote.sendMessage(request);
                    } else {
                        remote.sendMessage(requestWithPerson);
                    }

                    // Block until received response or timed out
                    Response response = waitForResponse();

                    if (response != null) {
                        outputManager.println(response.getMessage());
                        addCammand = response.getAddsCommand();
                        if(addCammand) {
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
                writeTrace(e);
                outputManager.println("Stopping...");
                return;
            }
        }
    }

    public void run() throws IOException {
        try (SocketChannel socket = SocketChannel.open()) {
            socket.connect(addr);
            socket.configureBlocking(false);
            remote = new ObjectSocketChannelWrapper(socket);

            inputCycle();
        }
    }
}
