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
        try {
            InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 8999);
            ConsoleClient console = new ConsoleClient(new UserInputManager(),new OutputManager(), addr);
            console.run();
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
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
