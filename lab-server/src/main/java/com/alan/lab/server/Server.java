package com.alan.lab.server;

import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.NoSuchElementException;

public final class Server {

    private static final int ARGSCOUNT = 6;
    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        String portS = System.getenv("PORT_PORT");
        String fileName = System.getenv("FILENAME");
        String dbHost = System.getenv("DB_HOST");
        String dbName = System.getenv("DB_NAME");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD"); //сервер не дает переменые окружения задать

        int i = 0;
        if (args.length != ARGSCOUNT) {
            System.out.println("port pg:5432 studs s352469 file_name passport");
            return;
        }
        try {
            Integer port;

            ServerInstance serverInstance = new ServerInstance(args[++i], args[++i], args[++i], args[++i], args[++i]);
            try {
                port = Integer.parseInt(args[0]);
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
