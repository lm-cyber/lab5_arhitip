package com.alan.lab.server;


import com.alan.lab.common.network.Request;
import com.alan.lab.common.network.Response;
import com.alan.lab.common.network.ResponseWithException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ServerInstance {
    private static final int SOCKET_TIMEOUT = 10;
    private final ForkJoinPool requestHandlerPool = new ForkJoinPool();
    private final ExecutorService responseSenderPool = Executors.newCachedThreadPool();
    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


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

    public void run(int port) throws IOException {
        Set<ClientThread> clients = new HashSet<>();

        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setSoTimeout(SOCKET_TIMEOUT);


            while (true) {
                // Accept input from console and stop server if needed
                if (acceptConsoleInput()) {
                    clients.forEach(x -> x.stop());
                    requestHandlerPool.shutdown();
                    responseSenderPool.shutdown();
                    return;
                }

                // Accept pending connections
                try {
                    while (true) {
                        Socket newClient = socket.accept();
                        newClient.setSoTimeout(SOCKET_TIMEOUT);
                        ClientThread client = new ClientThread(new ObjectSocketWrapper(newClient));
                        clients.add(client);
                        client.start();
                    }
                } catch (SocketTimeoutException e) {
                }
            }
        }
    }

    private class ClientThread {
        private final ObjectSocketWrapper socket;
        private final Thread thread;
        private boolean running = true;

        ClientThread(ObjectSocketWrapper socket) {
            this.socket = socket;
            this.thread = new Thread(this::handleRequests);
            this.thread.setName("Client" + this.socket.getSocket().getRemoteSocketAddress());
        }

        void start() {
            thread.start();
        }

        void stop() {
            running = false;
            try {
                socket.getSocket().close();
            } catch (IOException e) {
            }
        }

        void handleRequests() {
            while (running) {
                try {
                    if (socket.checkForMessage()) {
                        Object received = socket.getPayload();

                        if (received != null && received instanceof Request) {
                            Request request = (Request) received;
                            Response response = new Response("asdasd");
                            responseSenderPool.submit(() -> {
                                if (!socket.sendMessage(response)) {
                                }
                            });
                        } else {
                        }

                        socket.clearInBuffer();
                    }
                } catch (IOException e) {
                    stop();
                }
            }
        }
    }
}
