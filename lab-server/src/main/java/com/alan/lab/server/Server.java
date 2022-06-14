package com.alan.lab.server;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.NoSuchElementException;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {


        if (args.length != 2) {
            System.out.println("This program needs args.");
            return;
        }

        if (!args[0].endsWith(".json")) {
            System.out.println("This program can only work with .json file.");
            return;
        }
        try {
            Integer port;

            ServerInstance serverInstance = new ServerInstance(args[0]);
            try {
                port = Integer.parseInt(args[1]);
                serverInstance.run(port);
            } catch (IllegalArgumentException e) {
                System.out.println("bat port");
            }
        } catch (IOException e) {
            System.out.println("Could not read the file. Check if it is available.");
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            System.out.println("The file does not keep data in correct format.");
        } catch (
                NoSuchElementException e) {
            System.out.println("EOF");
        }

    }


}
