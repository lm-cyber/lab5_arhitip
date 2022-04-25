package com.alan.lab.server;


import com.alan.lab.common.network.Request;
import com.alan.lab.common.network.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;

public class ServerInstance {


 private final WorkWithCommand workWithCommand;
 private final HashSet<ObjectSocketWrapper> clients;

    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    public ServerInstance(String fileName) {

        clients = new HashSet<>();
        this.workWithCommand = new WorkWithCommand(fileName);
    }

    private boolean acceptConsoleInput() throws IOException {
        if (System.in.available() > 0) {
            String command = in.readLine();
            switch (command) {
                case "save":
                    break;
                case "exit":
                    System.out.println("Shutting down");
                    return true;
                default:
                    System.out.println("Unknown command. Available commands are: save, exit");
            }
        }

        return false;
    }

    public void handleRequests() throws IOException {
        Iterator<ObjectSocketWrapper> it = clients.iterator();
        while (it.hasNext()) {
            ObjectSocketWrapper client = it.next();

            try {
                if (client.checkForMessage()) {
                    Object received = client.getPayload();

                    if (received != null && received instanceof Request) {
                        Request request = (Request) received;
                        Response response =  new Response(workWithCommand.returnStringResponce(request.getCommandName(),request.getArgs()));
                        client.sendMessage(response);
                    } else {
                    }

                    client.clearInBuffer();
                }
            } catch (IOException e) {
                client.getSocket().close();
                it.remove();
            }
        }
    }

    public void run(int port) throws IOException {
        try (ServerSocketChannel channel = ServerSocketChannel.open();) {
            channel.bind(new InetSocketAddress(port));
            channel.configureBlocking(false);


            while (true) {
                // Accept input from console and stop server if needed
                if (acceptConsoleInput()) {
                    return;
                }

                // Accept pending connections
                SocketChannel newClient = null;
                while ((newClient = channel.accept()) != null) {
                    newClient.configureBlocking(false);
                    clients.add(new ObjectSocketWrapper(newClient));
                }

                // Handle new requests
                handleRequests();
            }
        }
    }
}
