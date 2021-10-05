package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Person;

/**
 * Interfaces with persons in database
 */
public class PersonDAO {
    /**
     * Connection to database
     */
    private final Connection conn;

    /**
     * Create new PersonDAO with given connection
     * @param conn connection with database
     */
    public PersonDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Insert person into databse
     * @param person Person to insert
     * @throws DataAccessException on failure to insert (person already exists or database failure)
     */
    public void insert(Person person) throws DataAccessException {

    }

    /**
     * Find person in database based on given personID
     * @param personID id of person to find
     * @return Person object found, null if not found
     */
    public Person getPersonByID(String personID) throws DataAccessException {
        return null;
    }

    /**
     * Delete all persons in the database
     * @throws DataAccessException on database failure or failure to delete
     */
    public void deletePersons() throws DataAccessException {}
}
