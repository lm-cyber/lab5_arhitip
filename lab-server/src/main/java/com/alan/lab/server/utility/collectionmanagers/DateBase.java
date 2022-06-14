package com.alan.lab.server.utility.collectionmanagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DateBase {
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS routes ("
            + "   id serial PRIMARY KEY,"
            + "   name varchar(100) NOT NULL,"
            + "   creation_date TIMESTAMP NOT NULL,"
            + "   from_name varchar(100) NOT NULL,"
            + "   from_coordinates_x bigint,"
            + "   from_coordinates_y double precision NOT NULL,"
            + "   from_coordinates_z bigint,"
            + "   to_name varchar(100) NOT NULL,"
            + "   to_coordinates_x bigint,"
            + "   to_coordinates_y double precision NOT NULL,"
            + "   to_coordinates_z bigint,"
            + "   distance double precision,"
            + "   owner_id integer NOT NULL,"
            + "   CONSTRAINT fk_owner"
            + "      FOREIGN KEY(owner_id) REFERENCES users(id) ON DELETE CASCADE)";
    private final Connection connection;

    public DateBase() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/personBD", "void", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void test() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM location");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
