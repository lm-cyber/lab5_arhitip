package com.alan.lab.server.utility.collectionmanagers;

import com.alan.lab.common.data.Color;
import com.alan.lab.common.data.Coordinates;
import com.alan.lab.common.data.Location;
import com.alan.lab.common.data.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Objects;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

public class SqlCollectionManager {

    private static final int NAMENUM = 1;
    private static final int COORDINATES_XNUM = 2;
    private static final int COORDINATES_YNUM = 3;
    private static final int CREATION_DATENUM = 4;
    private static final int HEOGHTNUM = 5;
    private static final int BIRTHDAYNUM = 6;
    private static final int PASSPORT_IDNUM = 7;
    private static final int HAIR_COLORNUM = 8;
    private static final int LOCATION_XNUM = 9;
    private static final int LOCATION_YNUM = 10;
    private static final int LOCATION_ZNUM = 11;
    private static final int OWNER_IDNUM = 12;

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS person("
            + "   id serial PRIMARY KEY,"
            + "   name varchar(100) NOT NULL,"
            + "   coordinates_x real NOT NULL,"
            + "   coordinates_y real NOT NULL,"
            + "   creation_date TIMESTAMP NOT NULL,"
            + "   height real,"
            + "   birthday TIMESTAMP NOT NULL,"
            + "   passport_id varchar(100) NOT NULL UNIQUE,"
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

                logger.info("Loaded " + collection.size() + " person from DB, removed " + invalidRoutes + " invalid person.");
            }
        }
    }

    private Person getPersonFromResultSet(ResultSet res) throws SQLException {
        Person.PersonBuilder personBuilder = Person.builder();
        personBuilder.id(res.getLong("id"));
        personBuilder.name(res.getString("name"));
        personBuilder.coordinates(new Coordinates(res.getFloat("coordinates_x"), res.getFloat("coordinates_y")));
        personBuilder.creationDate(res.getTimestamp("creation_date").toLocalDateTime());
        Float height = res.getFloat("height");
        if (height != null) {
            personBuilder.height(height);
        }
        personBuilder.birthday(res.getTimestamp("birthday").toLocalDateTime());
        personBuilder.passportID(res.getString("passport_id"));
        String color = res.getString("hair_color");
        if (color != null) {
            personBuilder.hairColor(Color.valueOf(color.toUpperCase()));
        }
        Double locationX = res.getDouble("location_x");
        if (locationX != null) {
            personBuilder.location(new Location(locationX, res.getInt("location_y"), res.getLong("location_z")));
        }
        personBuilder.ownerID(res.getLong("owner_id"));

        return personBuilder.build();
    }

    private void preparePersonStatement(PreparedStatement s, Person person, int paramOffset) throws SQLException {
        s.setString(paramOffset + NAMENUM, person.getName());
        s.setFloat(paramOffset + COORDINATES_XNUM, person.getCoordinates().getX());
        s.setFloat(paramOffset + COORDINATES_YNUM, person.getCoordinates().getY());
        s.setTimestamp(paramOffset + CREATION_DATENUM, Timestamp.valueOf(person.getCreationDate()));
        if (person.getHeight() != -1F) {
            s.setFloat(paramOffset + HEOGHTNUM, person.getHeight());
        } else {
            s.setNull(paramOffset + HEOGHTNUM, Types.FLOAT);
        }

        s.setTimestamp(paramOffset + BIRTHDAYNUM, Timestamp.valueOf(person.getBirthday()));
        s.setString(paramOffset + PASSPORT_IDNUM, person.getPassportID());
        if (person.getHairColor() != null) {
            s.setString(paramOffset + HAIR_COLORNUM, person.getHairColor().toString());
        } else {
            s.setNull(paramOffset + HAIR_COLORNUM, Types.VARCHAR);
        }
        if (person.getLocation() != null) {
            s.setDouble(paramOffset + LOCATION_XNUM, person.getLocation().getX());
            s.setInt(paramOffset + LOCATION_YNUM, person.getLocation().getY());
            s.setLong(paramOffset + LOCATION_ZNUM, person.getLocation().getZ());
        } else {
            s.setNull(paramOffset + LOCATION_XNUM, Types.DOUBLE);
            s.setNull(paramOffset + LOCATION_YNUM, Types.INTEGER);
            s.setNull(paramOffset + LOCATION_ZNUM, Types.BIGINT);
        }
        s.setLong(paramOffset + OWNER_IDNUM, person.getOwnerID());
    }

    public PriorityBlockingQueue<Person> getCollection() {
        return collection;
    }

    public Person getItemById(long id) {
        return collection.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public ResultOfSqlCollectionManager add(Person person) {
        String query = "INSERT INTO person VALUES ("
                + "    default,?,?,?,?,?,?,?,?,?,?,?,?) RETURNING id";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            preparePersonStatement(s, person, 0);
            try (ResultSet res = s.executeQuery()) {
                res.next();
                Long id = res.getLong("id");
                person.setId(id);
                collection.add(person);
                return ResultOfSqlCollectionManager.ADD_SUCCESS;
            }
        } catch (SQLException e) {
            logger.severe("Failed to insert element into DB" + e);
            return ResultOfSqlCollectionManager.PASSPORT_ID_CONTAINS;
        }
    }

    public Float minHeight() {
        String query = "SELECT MIN(height) FROM person";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            try (ResultSet res = s.executeQuery()) {
                res.next();
                return res.getFloat(1);
            }
        } catch (SQLException e) {
            logger.severe("problem" + e);
            return -1F;
        }
    }

    public Long checkOwnerId(Long id) {
        String query = "SELECT owner_id FROM person where id = ?";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            s.setLong(1, id);
            try (ResultSet res = s.executeQuery()) {
                res.next();
                return res.getLong(1);
            }
        } catch (SQLException e) {
            logger.severe("problem" + e);
            return -1L;
        }
    }

    public ResultOfSqlCollectionManager addIfMin(Person person) {
        if (person.getHeight() < minHeight()) {
            return add(person);
        }
        return ResultOfSqlCollectionManager.PERSON_NOT_MIN;
    }

    public ResultOfSqlCollectionManager isEmpty() {
        String query = "SELECT COUNT(id) FROM person";

        try (PreparedStatement s = conn.prepareStatement(query)) {
            try (ResultSet res = s.executeQuery()) {
                res.next();
                return res.getLong(1) == 0 ? ResultOfSqlCollectionManager.IS_EMPTY_TRUE : ResultOfSqlCollectionManager.IS_EMPTY_FALSE;
            }
        } catch (SQLException e) {
            logger.severe("problem" + e);
            return ResultOfSqlCollectionManager.IS_EMPTY_ERROR;
        }

    }

    public boolean update(Long id, Long ownerId) {
        return Objects.equals(ownerId, checkOwnerId(id));
    }

    public ResultOfSqlCollectionManager update(Person person) {
        if (Objects.equals(person.getOwnerID(), checkOwnerId(person.getId()))) {
            final int idOffset = 12;
            String query = "UPDATE person SET "
                    + "name=?, "
                    + "coordinates_x=?, "
                    + "coordinates_y=?, "
                    + "creation_date=?, "
                    + "height=?, "
                    + "birthday=?, "
                    + "passport_id=?, "
                    + "hair_color=?, "
                    + "location_x=?, "
                    + "location_y=?, "
                    + "location_z=? "
                    + "WHERE id=?";

            try (PreparedStatement s = conn.prepareStatement(query)) {
                preparePersonStatement(s, person, 0);
                s.setLong(idOffset, person.getId());
                s.executeUpdate();
                return ResultOfSqlCollectionManager.UPDATE_SUCCESS;
            } catch (SQLException e) {
                logger.severe("Failed to update person " + e);
                return ResultOfSqlCollectionManager.UPDATE_ERROR;
            }
        }
        return ResultOfSqlCollectionManager.UPDATE_NOT_OWNER;
    }


    public ResultOfSqlCollectionManager remove(Long id, Long userId) {
        if (userId.equals(checkOwnerId(id))) {
            String query = "DELETE FROM person WHERE id=?";

            try (PreparedStatement s = conn.prepareStatement(query)) {
                s.setLong(1, id);
                s.executeUpdate();
                return ResultOfSqlCollectionManager.REMOVE_SUCCESS;
            } catch (SQLException e) {
                logger.severe("Failed to delete row" + e);
                return ResultOfSqlCollectionManager.REMOVE_ERROR;
            }
        }
        return ResultOfSqlCollectionManager.REMOVE_NOT_OWNER;
    }


    public ResultOfSqlCollectionManager clear() {
        try (Statement s = conn.createStatement()) {
            boolean prev = conn.getAutoCommit();
            conn.setAutoCommit(false);
            s.execute("DROP TABLE person");
            s.execute(CREATE_TABLE_QUERY);
            conn.commit();
            conn.setAutoCommit(prev);
            collection.clear();
            return ResultOfSqlCollectionManager.CLEAR_SUCCESS;
        } catch (SQLException e) {
            try {
                logger.severe("Failed to clear table, rolling back..." + e);
                conn.rollback();
                return ResultOfSqlCollectionManager.CLEAR_ERROR_ROLLBACK;
            } catch (SQLException e_) {
                logger.severe("Failed to rollback" + e);
                return ResultOfSqlCollectionManager.CLEAR_ERROR;
            }
        }
    }
}
