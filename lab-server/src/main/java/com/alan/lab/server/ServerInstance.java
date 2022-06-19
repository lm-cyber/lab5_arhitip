package com.alan.lab.server;


import com.alan.lab.common.data.Person;
import com.alan.lab.common.network.Request;
import com.alan.lab.common.network.RequestWithPerson;
import com.alan.lab.common.network.Response;
import com.alan.lab.common.utility.nonstandardcommand.NonStandardCommand;
import com.alan.lab.server.utility.HistoryManager;
import com.alan.lab.server.utility.NonStandardCommandServer;
import com.alan.lab.server.utility.collectionmanagers.CollectionManager;
import com.alan.lab.server.utility.collectionmanagers.FileManager;
import com.alan.lab.server.utility.collectionmanagers.SqlCollectionManager;
import com.alan.lab.server.utility.usermanagers.SqlUserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class ServerInstance {

    private static final int TIMEOUTWRITE = 100;
    private static final int SOCKET_TIMEOUT = 10;
    private final ResponseCreator responseCreator;
    private final FileManager fileManager;

    private final CollectionManager collectionManager;
    private final HashSet<ObjectSocketWrapper> clients;
    private final Logger logger;
    private final NonStandardCommand nonStandardCommandServer;
    private SqlUserManager sqlUserManager;
    private Connection connection;
    private SqlCollectionManager sqlCollectionManager;


    public ServerInstance(String fileName) {
        this.collectionManager = new CollectionManager();
        this.fileManager = new FileManager(fileName);
        clients = new HashSet<>();
        this.logger = Logger.getLogger("log");
        this.nonStandardCommandServer = new NonStandardCommandServer(collectionManager, logger, fileManager);
        File lf = new File("server.log");
        FileHandler fh = null;
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/personBD", "void", "");
            this.sqlCollectionManager = new SqlCollectionManager(connection, logger);
            this.sqlUserManager = new SqlUserManager(connection,logger);
            fh = new FileHandler(lf.getAbsolutePath(), true);
            logger.addHandler(fh);
        } catch (IOException e) {
            System.out.println(e.getMessage() + "logger not write in file");
        } catch (SQLException e) {
            System.out.println("SQL err");
            System.exit(1);
        }
        this.responseCreator = new ResponseCreator(new HistoryManager(), collectionManager, sqlUserManager, sqlCollectionManager);
    }

    private void start() throws FileNotFoundException {
        try {
            this.sqlCollectionManager.initTable();
        } catch (SQLException e) {
            logger.severe("init table pisec" + e);
        }
        PriorityBlockingQueue<Person> people = new PriorityBlockingQueue<>(sqlCollectionManager.getCollection());

        collectionManager.initialiseData(people);
    }


    public void handleRequests() throws IOException {
        Iterator<ObjectSocketWrapper> it = clients.iterator();
        while (it.hasNext()) {
            ObjectSocketWrapper client = it.next();
            try {
                if (client.checkForMessage()) {
                    Object received = client.getPayload();
                    logger.info("get Payload");
                    if (received instanceof RequestWithPerson) {
                        sendResponseWithPerson(received, client);
                    } else if (received instanceof Request) {
                        sendResponse(received, client);
                    }
                    client.clearInBuffer();
                }
            } catch (IOException e) {
                client.getSocket().close();
                it.remove();
            }
        }
    }

    private void sendResponse(Object received, ObjectSocketWrapper client) throws IOException {
        Request request = (Request) received;
        responseCreator.addHistory(request.getCommandName() + " " + request.getArgs().toString());
        logger.info("doing " + request.getCommandName() + " " + request.getArgs().toString());
        Response response = responseCreator.executeCommand(request.getCommandName(), request.getArgs(), request.getAuthCredentials());
        client.sendMessage(response);
        logger.fine("send message");
    }
    private void sendResponseWithPerson(Object received,ObjectSocketWrapper client) throws IOException {
        logger.info("request with person");
        RequestWithPerson requestWithPerson = (RequestWithPerson) received;
        Response response = responseCreator.executeCommandWithPerson(requestWithPerson.getType(), requestWithPerson.getPerson(),
                requestWithPerson.getAuthCredentials());
        client.sendMessage(response);
    }

    public void run(int port) throws IOException {
        int check = 0;
        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setSoTimeout(SOCKET_TIMEOUT);
            start();
            logger.info("Server is listening on port " + port);
            while (true) {
                if (nonStandardCommandServer.execute(null)) {
                    return;
                }
                try {
                    while (true) {
                        Socket newClient = socket.accept();
                        newClient.setSoTimeout(SOCKET_TIMEOUT);
                        logger.info("Received connection from " + newClient.getRemoteSocketAddress());
                        clients.add(new ObjectSocketWrapper(newClient));
                    }
                } catch (SocketTimeoutException e) {
                    if (check++ >= TIMEOUTWRITE) {
                        check = 0;
                    }
                }
                handleRequests();
            }
        }
    }
}
