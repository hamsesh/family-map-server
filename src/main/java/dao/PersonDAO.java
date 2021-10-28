package dao;

import java.sql.*;

import model.Person;
import model.User;

/**
 * Interfaces with persons in database
 */
public class PersonDAO {
    /**
     * Connection to database
     */
    private Connection conn;

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
        String sqlStmt = "insert into persons (person_id, username, first_name, last_name, gender, father_id," +
                " mother_id, spouse_id) values (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sqlStmt)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Find person in database based on given personID
     * @param personID id of person to find
     * @return Person object found, null if not found
     */
    public Person getPersonByID(String personID) throws DataAccessException {
        String sql = "select * from persons where person_id = '" + personID + "'";
        ResultSet rs = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String associatedUsername = rs.getString(2);
            String firstName = rs.getString(3);
            String lastName = rs.getString(4);
            String gender = rs.getString(5);
            String fatherID = rs.getString(6);
            String motherID = rs.getString(7);
            String spouseID = rs.getString(8);

            return new Person(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
        }
        catch (SQLException e) {
            throw new DataAccessException("Unable to get person by given ID");
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Delete all persons in the database
     * @throws DataAccessException on database failure or failure to delete
     */
    public void deletePersons() throws DataAccessException {
        String sql = "delete from persons";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error deleting persons");
        }
    }

    public Person[] getAllPersonsByUsername(String username) throws DataAccessException {
        String countSql = "select count(*) from persons where username = '" + username + "'";
        String sql = "select * from persons where username = '" + username + "'";
        ResultSet rs = null;
        Person[] foundPersons;
        int numPersons;

        try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
            rs = countStmt.executeQuery();
            numPersons = rs.getInt(1);
        }
        catch (SQLException e) {
            throw new DataAccessException("Unable to count persons");
        }

        if (numPersons == 0) return null;
        else foundPersons = new Person[numPersons];

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                String person_id = rs.getString(1);
                String associatedUsername = rs.getString(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                String gender = rs.getString(5);
                String fatherID = rs.getString(6);
                String motherID = rs.getString(7);
                String spouseID = rs.getString(8);
                foundPersons[i] = new Person(person_id, associatedUsername, firstName, lastName,
                                            gender, fatherID, motherID, spouseID);
                i++;
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Error getting persons");
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return foundPersons;
    }

    /**
     * Delete all persons associated to username
     */
    public void deletePersonsByUsername(String username) throws DataAccessException {
        String sql = "delete from persons where username = '" + username + "'";
        try {
            Statement foreignStmt = conn.createStatement();
            foreignStmt.executeUpdate("PRAGMA foreign_keys=ON;");
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Set the connection
     * @param conn New connection
     */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
