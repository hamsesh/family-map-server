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
    private Connection conn;

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
        String sqlStmt = "insert into auth_tokens (auth_token, username) " +
                "values (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sqlStmt)) {
            stmt.setString(1, token.getToken());
            stmt.setString(2, token.getUsername());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Checks to see if token exists and matches the given user ID
     * @param token AuthToken string to validate
     * @return username if token exists and matches the user ID, else returns null
     * @throws DataAccessException on database failure or invalid data
     */
    public String validate(String token) throws DataAccessException {
        String sql = "select auth_token, username from auth_tokens where auth_token = '" + token + "'";
        ResultSet rs = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            if (rs.next()) {
                String foundToken = rs.getString(1);
                String username = rs.getString(2);
                if (token.equals(foundToken)) {
                    return username;
                }
                return null;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DataAccessException("Unable to get user by given username");
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
     * Delete all authTokens in the database
     * @throws DataAccessException on database failure or failure to delete
     */
    public void deleteAuthTokens() throws DataAccessException {
        String sql = "delete from auth_tokens";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Error deleting auth tokens");
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
