

package dao;

import org.sqlite.SQLiteConfig;

import java.io.File;
import java.sql.*;

/**
 * Handles opening and closing of database connections
 */
public class Database {
    /**
     * Connection to database
     */
    private Connection conn;

    /**
     * Create new database and initialize connection
     */
    public Database() {
        conn = null;
    }

    /**
     * Open connection to database
     * @return opened connection
     * @throws DataAccessException on failure to open connection with database
     */
    public Connection open() throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            String dbPath = "sql" + File.separator + "prod-db.db";
            final String CONNECTION_URL = "jdbc:sqlite:" + dbPath;

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection open(String dbPath) throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:" + dbPath;

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection openWithForeignKey(String dbPath) throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:" + dbPath;

            // Open a database connection to the file given in the path
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(CONNECTION_URL, config.toProperties());

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    public Connection getConnection() throws DataAccessException {
        if(conn == null) {
            return open();
        } else {
            return conn;
        }
    }

    /**
     * Close current open connection
     * @param commit Commit current changes
     * @throws DataAccessException on failure to close connection or commit changes
     */
    public void close(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    /**
     * Clear all tables in database
     * @throws DataAccessException on database failure or failure to drop tables
     */
    public void clearTables() throws DataAccessException
    {
        try {
            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM users");
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM events");
            PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM persons");
            PreparedStatement stmt4 = conn.prepareStatement("DELETE FROM auth_tokens");
            stmt1.executeUpdate();
            stmt2.executeUpdate();
            stmt3.executeUpdate();
            stmt4.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Unable to clear tables");
        }
    }
}

