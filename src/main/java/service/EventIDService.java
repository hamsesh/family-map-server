package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.EventDAO;
import model.Event;
import result.EventIDResult;

import java.text.Normalizer;


/**
 * Performs eventID action
 */
public class EventIDService extends Service{
    /**
     * Create new EventIDService object
     */
    public EventIDService(String dbPath) {
        super(dbPath);
    }

    /**
     * Gets a single event object with the specified id
     * @param token Authentication token of current user
     * @param eventID ID for the event
     * @return a single event object with the specified id
     */
    public EventIDResult eventID(String token, String eventID) throws DataAccessException {
        Database db = new Database();
        try {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.open(dbPath));
            String username = authTokenDAO.validate(token);
            if (username == null) {
                return new EventIDResult("Error: Unable to authenticate user", false);
            }

            EventDAO eventDAO = new EventDAO(db.getConnection());
            Event event = eventDAO.getEventByID(eventID);
            if (event == null || !event.getUsername().equals(username)) {
                return new EventIDResult("Error: Unable to find event with given ID", false);
            }

            return new EventIDResult(event.getUsername(), event.getEventID(), event.getPersonID(), event.getLatitude(),
                    event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(),
                    event.getYear(), null, true);
        }
        catch (DataAccessException e) {
            return new EventIDResult("Error: Unable to authenticate user", false);
        }
        finally {
            db.close(false);
        }
    }
}
