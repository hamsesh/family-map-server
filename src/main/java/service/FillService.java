package service;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import request.FillRequest;
import request.RequestException;
import result.FillResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Performs fill action
 */
public class FillService {
    /**
     * Path to database
     */
    private final String dbPath;

    /**
     * Create new FillService object
     */
    public FillService(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * Populates the server database with generated data for the specified username
     * @param request FillRequest containing username and number of generations to fill
     * @return the result of the fill request
     */
    public FillResult fill(FillRequest request) throws RequestException {
        if (request.getGenerations() < 0) {
            throw new RequestException("Error: Invalid number of generations");
        }
        try {
            //FIXME: delete any existing data for user
            Database db = new Database();
            Connection conn = db.open(dbPath);
            PersonDAO personDAO = new PersonDAO(conn);
            //FIXME: Do the actual filling
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            return new FillResult("Error: Unable to access database!", false);
        }
        return new FillResult("Successfully added X persons and Y events to the database", true);
    }
}
