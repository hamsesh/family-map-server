package service;

import dao.DataAccessException;
import dao.Database;
import result.ClearResult;

/**
 * Performs clear action
 */
public class ClearService extends Service {
    /**
     * Create new ClearService object
     * @param dbPath Path to the database
     */
    public ClearService(String dbPath) {
        super(dbPath);
    }

    /**
     * Delete ALL data from the database, including user accounts, auth tokens, and person/event data
     * @return the result of the clear request
     */
    public ClearResult clear() {
        try {
            Database db = new Database();
            db.open(dbPath);
            db.clearTables();
            db.close(true);
        }
        catch (DataAccessException e) {
            return new ClearResult("Error: " + e.getMessage(), false);
        }
        return new ClearResult("Clear succeeded", true);
    }
}
