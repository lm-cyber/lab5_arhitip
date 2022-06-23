package com.alan.lab.server;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.NoSuchElementException;

public final class Server {

    private static final int ARGSCOUNT = 6;
    private static final int PORTNUM = 0;
    private static final int FILENAMENUM = 1;
    private static final int HOSTNAMENUM = 2;
    private static final int DATABASENAMENUM = 3;
    private static final int USERDBNAMENUM = 4;
    private static final int PASSPORTNAMEMUN = 5;
    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        if (args.length != ARGSCOUNT) {
            System.out.println("port file_name pg:5432 studs s352469 passport");
            return;
        }
        try {
            Integer port;

            ServerInstance serverInstance = new ServerInstance(args[FILENAMENUM], args[HOSTNAMENUM], args[DATABASENAMENUM], args[USERDBNAMENUM], args[PASSPORTNAMEMUN]);
            try {
                port = Integer.parseInt(args[PORTNUM]);
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
