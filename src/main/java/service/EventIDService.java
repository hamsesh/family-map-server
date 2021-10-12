package service;

import model.AuthToken;
import result.EventIDResult;

/**
 * Performs eventID action
 */
public class EventIDService {
    /**
     * Create new EventIDService object
     */
    public EventIDService() {}

    /**
     * Gets a single event object with the specified id
     * @param token Authentication token of current user
     * @param eventID ID for the event
     * @return a single event object with the specified id
     */
    EventIDResult eventID(AuthToken token, String eventID) {
        return null;
    }
}
