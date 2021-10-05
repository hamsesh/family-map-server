package service;

import model.AuthToken;
import result.EventResult;

/**
 * Performs event action
 */
public class EventService {
    /**
     * Create new EventService object
     */
    public EventService() {}

    /**
     * Get events of all family members for current user
     * @param token Authentication token of current user
     * @return all events for all family members of the current user
     */
    EventResult event(AuthToken token) {
        return null;
    }
}
