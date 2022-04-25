package com.alan.lab.server;

import java.io.IOException;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException {
       ServerInstance serverInstance = new ServerInstance(args[0]);
       serverInstance.run(8999);

    }



}
