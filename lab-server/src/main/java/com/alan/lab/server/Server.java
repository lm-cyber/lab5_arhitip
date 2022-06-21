package com.alan.lab.server;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.NoSuchElementException;

public final class Server {

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        String portS = System.getenv("PORT");
        String fileName = System.getenv("FILENAME");
        String dbHost = System.getenv("DB_HOST");
        String dbName = System.getenv("DB_NAME");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        try {
            Integer port;

            ServerInstance serverInstance = new ServerInstance(fileName, dbHost, dbName, dbUser, dbPassword);
            try {
                port = Integer.parseInt(portS);
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
    /* public static void main1(String[] args) throws IOException {
        String fileName = System.getenv("FILENAME");
        String dbHost = System.getenv("DB_HOST");
        String dbName = System.getenv("DB_NAME");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");
        int port;

        try {
            port = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.exit(1);
            return;
        }

        // Prioritize SQL
        if (dbHost != null && dbName != null && dbUser != null && dbPassword != null) {
            startSQLCollectionServer(port, dbHost, dbName, dbUser, dbPassword);
        } else {
            System.exit(1);
        }
    }

    private static void startSQLCollectionServer(int port, String dbHost, String dbName, String dbUser, String dbPassword) throws IOException {
        try (Connection conn = DriverManager.getConnection(
            "jdbc:postgresql://" + dbHost + '/' + dbName,
            dbUser,
            dbPassword
        )) {
            // Create users table first
            SqlUserManager users = new SqlUserManager(conn);
            ServerInstance server = new ServerInstance(col, users);
            server.run(port);
        } catch (SQLException e) {
            System.exit(1);
        }
    }*/


}
