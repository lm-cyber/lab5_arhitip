package com.alan.lab.server.utility.collectionmanagers;

import com.alan.lab.common.data.Color;
import com.alan.lab.common.data.Coordinates;
import com.alan.lab.common.data.Location;
import com.alan.lab.common.data.Person;

import java.sql.*;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SqlCollectionManager {
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS person("
            + "   id serial PRIMARY KEY,"
            + "   name varchar(100) NOT NULL,"
            + "   coordinates_x real NOT NULL,"
            + "   coordinates_y real NOT NULL,"
            + "   creation_date TIMESTAMP NOT NULL,"
            + "   height real,"
            + "   birthday TIMESTAMP NOT NULL,"
            + "   passport_id varchar(100) NOT NULL,"
            + "   hair_color varchar(20) ,"
            + "   location_x double precision,"
            + "   location_y integer,"
            + "   location_z bigint,"
            + "   owner_id integer NOT NULL,"
            + "   CONSTRAINT fk_owner"
            + "      FOREIGN KEY(owner_id) REFERENCES users(id) ON DELETE CASCADE)";
    private final Logger logger;
    private final Connection conn;
    private final PriorityBlockingQueue<Person> collection = new PriorityBlockingQueue<>();

    public SqlCollectionManager(Connection conn, Logger logger) {
        this.conn = conn;
        this.logger = logger;
    }

    public void initTable() throws SQLException {
        // Setup tables
        try (Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            s.execute(CREATE_TABLE_QUERY);

            try (ResultSet res = s.executeQuery("SELECT * FROM person")) {
                int invalidRoutes = 0;

                while (res.next()) {
                    Person person = getPersonFromResultSet(res);
                    if (person != null) {
                        collection.add(person);
                    } else {
                        invalidRoutes++;
                    }
                }

                logger.info("Loaded " + collection.size() + " routes from DB, removed " + invalidRoutes + " invalid routes.");
            }
        }
    }

    private Person getPersonFromResultSet(ResultSet res) throws SQLException {
        Person.PersonBuilder personBuilder = Person.builder();
        personBuilder.id(res.getLong("id"));
        personBuilder.name(res.getString("name"));
        personBuilder.coordinates(new Coordinates(res.getFloat("coordinates_x"),res.getFloat("coordinates_y")));
        personBuilder.creationDate(res.getTimestamp("creation_date").toLocalDateTime());
        Float height = res.getFloat("height");
        if(height != null) {
            personBuilder.height(height);
        }
        personBuilder.birthday(res.getTimestamp("birthday").toLocalDateTime());
        personBuilder.passportID(res.getString("passport_id"));
        String color = res.getString("hair_color");
        if(color != null) {
            personBuilder.hairColor(Color.valueOf(color.toUpperCase()));
        }
        Double locationX = res.getDouble("location_x");
        if(locationX != null) {
            personBuilder.location(new Location(locationX,res.getInt("location_y"),res.getLong("location_z")));
        }
        personBuilder.ownerID(res.getLong("owner_id"));

        return personBuilder.build();
    }

    private void preparePersonStatement(PreparedStatement s, Person person, int paramOffset) throws SQLException {
        int i = 0;
        s.setString(paramOffset + ++i, person.getName());
        s.setFloat(paramOffset + ++i, person.getCoordinates().getX());
        s.setFloat(paramOffset + ++i, person.getCoordinates().getY());
        s.setTimestamp(paramOffset + ++i, Timestamp.valueOf(person.getCreationDate()));
        if (person.getHeight() != -1F) {
            s.setFloat(paramOffset + ++i, person.getHeight());
        } else {
            s.setNull(paramOffset + ++i, Types.FLOAT);
        }

        s.setTimestamp(paramOffset + ++i, Timestamp.valueOf(person.getBirthday()));
        s.setString(paramOffset + ++i, person.getPassportID());
        if (person.getHairColor() != null) {
            s.setString(paramOffset + ++i, person.getHairColor().toString());
        } else {
            s.setNull(paramOffset + ++i, Types.VARCHAR);
        }
        if (person.getLocation() != null) {
            s.setDouble(paramOffset + ++i, person.getLocation().getX());
            s.setInt(paramOffset + ++i, person.getLocation().getY());
            s.setLong(paramOffset + ++i, person.getLocation().getZ());
        } else {
            s.setNull(paramOffset + ++i, Types.DOUBLE);
            s.setNull(paramOffset + ++i, Types.INTEGER);
            s.setNull(paramOffset + ++i, Types.BIGINT);
        }
        s.setLong(paramOffset + ++i, person.getOwnerID());
    }

    public PriorityBlockingQueue<Person> getCollection() {
            return collection;
    }

    public Person getItemById(long id) {
        try {
            return collection.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        } finally {
        }
    }

    public long add(Person person) {
        String query = "INSERT INTO person VALUES ("
                     + "    default,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            preparePersonStatement(s, person, 0);
            try (ResultSet res = s.executeQuery()) {
                res.next();
                Long id = res.getLong("id");
                person.setId(id);
                collection.add(person);
                return id;
            }
        } catch (SQLException e) {
            logger.severe("Failed to insert element into DB" + e);
            return 0;
        }
    }

    public boolean update(Person person) {
        final int idOffset = 12;
        String query = "UPDATE routes SET "
                     + "name=?, "
                     + "creation_date=?, "
                     + "from_name=?, "
                     + "from_coordinates_x=?, "
                     + "from_coordinates_y=?, "
                     + "from_coordinates_z=?, "
                     + "to_name=?, "
                     + "to_coordinates_x=?, "
                     + "to_coordinates_y=?, "
                     + "to_coordinates_z=?, "
                     + "distance=? "
                     + "WHERE id=?";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            preparePersonStatement(s, person, 0);
            s.setLong(idOffset, person.getId());
            int count = s.executeUpdate();
            if (count > 0) {
                collection.removeIf(x -> x.getId() == person.getId());
                collection.add(person);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            logger.severe("Failed to update route" + e);
            return false;
        }
    }



    public void remove(long id) {
        String query = "DELETE FROM person WHERE id=?";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            s.setLong(1, id);
            s.executeUpdate();
            collection.removeIf(x -> x.getId() == id);
        } catch (SQLException e) {
            logger.severe("Failed to delete row" + e);
        }
    }

    public int removeIf(Predicate<? super Person> predicate) {
            List<Long> ids = collection.stream().filter(predicate).map(x -> x.getId()).collect(Collectors.toList());
            ids.forEach(this::remove);
            return ids.size();
    }

    public void clear() {
        try (Statement s = conn.createStatement()) {
            boolean prev = conn.getAutoCommit();
            conn.setAutoCommit(false);
            s.execute("DROP TABLE person");
            s.execute(CREATE_TABLE_QUERY);
            conn.commit();
            conn.setAutoCommit(prev);
            collection.clear();
        } catch (SQLException e) {
            try {
                logger.severe("Failed to clear table, rolling back..." + e);
                conn.rollback();
            } catch (SQLException e_) {
                logger.severe("Failed to rollback" + e);
            }
        }
    }
}
