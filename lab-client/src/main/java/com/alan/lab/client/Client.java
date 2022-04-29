package com.alan.lab.client;


import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.TerminalColors;
import com.alan.lab.common.utility.UserInputManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("args problem");
            return;
        }
        try {
            Integer port = Integer.parseInt(args[1]);
            InetSocketAddress addr = new InetSocketAddress(args[0], port);
            ConsoleClient console = new ConsoleClient(new UserInputManager(), new OutputManager(), addr);
            console.run();

        } catch (NumberFormatException e) {
            System.out.println("bad port");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(
                    TerminalColors.colorString("Unable to parse host address and port from arguments. You should pass them in as arguments", TerminalColors.RED)
            );
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(
                    TerminalColors.colorString("Failed to launch app", TerminalColors.RED)
            );
            e.printStackTrace();
            return;
        }
    }
}
