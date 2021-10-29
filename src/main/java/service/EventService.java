package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import model.AuthToken;
import model.Event;
import result.EventResult;

/**
 * Performs event action
 */
public class EventService extends Service {
    /**
     * Create new EventService object
     */
    public EventService(String dbPath) {
        super(dbPath);
    }

    /**
     * Get all events associated with current user
     * @param token Authentication token for current user
     * @return the result of request containing array of events
     */
    public EventResult event(String token) throws DataAccessException {
        Database db = new Database();
        try {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.open(dbPath));
            String username = authTokenDAO.validate(token);
            if (username == null) {
                return new EventResult(null, "Error: Unable to authenticate user", false);
            }

            EventDAO eventDAO = new EventDAO(db.getConnection());
            Event[] events = eventDAO.getAllEventsByUsername(username);
            if (events == null) {
                return new EventResult(null, "Error: Unable to find events", false);
            }
            return new EventResult(events, null, true);
        }
        catch (DataAccessException e) {
            return new EventResult(null, "Error: Unable to authenticate user", false);
        }
        finally {
            db.close(false);
        }
    }
}
