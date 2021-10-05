package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

/**
 * Interfaces with users in database
 */
public class UserDAO {
    /**
     * Connection to database
     */
    private final Connection conn;

    /**
     * Create new UserDAO with given connection
     * @param conn connection with database
     */
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Validate the given username/password combination
     * @param username User-entered username
     * @param password User-entered password
     * @return id of user matching the username and password, null if username and password don't match
     * or if user doesn't exist
     * @throws DataAccessException on invalid data or database failure
     */
    public String validate(String username, String password) throws DataAccessException {
        return null;
    }

    /**
     * Add a user to the database
     * @param user User object with data
     * @throws DataAccessException on user already exists, invalid data, or database failure
     */
    public void createUser(User user) throws DataAccessException {}

    /**
     * Get a user from the database by ID
     * @param userID ID of user
     * @return the user that matches the ID, null if not found
     * @throws DataAccessException on invalid data or database failure
     */
    public User getUserByID(String userID) throws DataAccessException {
        return null;
    }

    /**
     * Delete all users in the database
     * @throws DataAccessException on database failure or failure to delete
     */
    public void deleteUsers() throws DataAccessException {

    }
}
