package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.AuthToken;

/**
 * Interfaces with auth tokens in database
 */
public class AuthTokenDAO {
    /**
     * Connection to database
     */
    private final Connection conn;

    /**
     * Create new AuthTokenDAO with given connection
     * @param conn connection to database
     */
    public AuthTokenDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Insert auth token into database
     * @param token AuthToken to insert
     * @throws DataAccessException on failure to insert (token already exists or database failure)
     */
    public void insert(AuthToken token) throws DataAccessException {
    }

    /**
     * Checks to see if token exists and matches the given user ID
     * @param token AuthToken to validate
     * @return true if token exists and matches the user ID
     * @throws DataAccessException on database failure or invalid data
     */
    public boolean validate(AuthToken token) throws DataAccessException {
        return false;
    }

    /**
     * Delete all authTokens in the database
     * @throws DataAccessException on database failure or failure to delete
     */
    public void deleteAuthTokens() throws DataAccessException {}
}
