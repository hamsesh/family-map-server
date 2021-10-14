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
    private Connection conn;

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
    public void insert(User user) throws DataAccessException {
        String sqlStmt = "insert into users (username, passwd, email, first_name, last_name, gender, user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sqlStmt)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getUserID());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Get a user from the database by username
     * @param username username of user
     * @return the user that matches the username, null if not found
     */
    public User getUserByUsername(String username) throws DataAccessException {
        String sql = "select username, passwd, email, first_name, last_name, gender, user_id " +
                "from users where username = '" + username + "'";
        User foundUser;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            String passwd = rs.getString(2);
            String email = rs.getString(3);
            String firstName = rs.getString(4);
            String lastName = rs.getString(5);
            String gender = rs.getString(6);
            String userID = rs.getString(7);
            return new User(username, passwd, email, firstName, lastName, gender, userID);
        }
        catch (SQLException e) {
            throw new DataAccessException("Unable to get user by given username");
        }
    }

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
        String sql = "delete from users";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error deleting users");
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
