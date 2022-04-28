package com.alan.lab.server;


import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Request;
import com.alan.lab.common.network.RequestWithPerson;
import com.alan.lab.common.network.Response;
import com.alan.lab.server.utility.CollectionManager;
import com.alan.lab.server.utility.FileManager;
import com.alan.lab.server.utility.HistoryManager;
import com.alan.lab.server.utility.JsonParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

public class ServerInstance {

    private final ResponseCreator responseCreator;
    private final FileManager fileManager;
    private final CollectionManager collectionManager;
 private final HashSet<ObjectSocketWrapper> clients;

    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    public ServerInstance( String fileName) {
        this.collectionManager = new CollectionManager();
        this.fileManager = new FileManager(fileName);
        this.responseCreator = new ResponseCreator(new HistoryManager(),collectionManager);
        clients = new HashSet<>();
    }
    private void start() {
        StringBuilder stringData = null;
        try {
            stringData = fileManager.read();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        PriorityQueue<Person> people = JsonParser.toData(String.valueOf(stringData));
        collectionManager.initialiseData(people);
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

                    if (received instanceof RequestWithPerson) {
                        RequestWithPerson requestWithPerson = (RequestWithPerson) received;
                        Response response ;
                        switch (requestWithPerson.getType()) {
                            case ADD :
                                response = responseCreator.add(requestWithPerson.getPerson());
                                break;
                            case UPDATE:
                                response = responseCreator.update(requestWithPerson.getPerson());
                                break;
                            case ADD_IF_MIN:
                                response = responseCreator.addIfMin(requestWithPerson.getPerson());
                                break;
                            default:
                                response = new Response("something bad",false);
                        }
                        client.sendMessage(response);
                    } else if(received instanceof Request) {
                        Request request = (Request) received;
                        responseCreator.addHistory(request.getCommandName()+ " "+ request.getArgs().toString());
                        Response response = responseCreator.executeCommand(request.getCommandName(),request.getArgs());
                        client.sendMessage(response);

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
            start();


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
