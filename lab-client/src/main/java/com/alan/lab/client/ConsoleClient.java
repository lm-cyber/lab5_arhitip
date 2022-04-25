package com.alan.lab.client;

import com.alan.lab.common.commands.subcommands.AddElem;
import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Request;
import com.alan.lab.common.network.RequestWithPerson;
import com.alan.lab.common.network.Response;
import com.alan.lab.common.network.ResponseWithException;
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

    private void handleResponse(Response response) {
        outputManager.println(response.getMessage());

        if (response instanceof ResponseWithException) {
            ResponseWithException rwe = (ResponseWithException) response;

            writeTrace(rwe.getException());
        }
    }
 /*commands.add(new AddCommand(collectionManager, userInputManager, outputManager));
        commands.add(new UpdateCommand(collectionManager, userInputManager, outputManager));
        commands.add(new AddIfMinCommand(collectionManager, userInputManager, outputManager));
        */
    private void inputCycle() {
        String input;
        while ((input = userInputManager.nextLine()) != null) {
            try {
                if(input.equals("exit")) {
                    return;
                }
                if(input.startsWith("execute_script")){
                    userInputManager.connectToFile(new File(input.split(" ",2)[1]));
                }
                ParseToNameAndArg parseToNameAndArg = new ParseToNameAndArg(input);
                Request request ;
                if(parseToNameAndArg.getName().equals("add") ||parseToNameAndArg.getName().equals("add_if_min") ||parseToNameAndArg.getName().equals("update") )
                {
                    Person person = AddElem.add(userInputManager,outputManager);
                    request = new RequestWithPerson(parseToNameAndArg.getName(),parseToNameAndArg.getArg(),person);
                } else {
                    request = new Request(parseToNameAndArg.getName(),parseToNameAndArg.getArg());
                }

                // If the command is not only client-side
                if (request != null) {
                    remote.sendMessage(request);
                    // Block until received response or timed out
                    Response response = waitForResponse();

                    if (response != null) {
                        handleResponse(response);
                    } else {
                        outputManager.println("Request failed");
                    }

                    remote.clearInBuffer();
                }
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
