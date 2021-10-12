package service;

import request.LoadRequest;
import result.LoadResult;

/**
 * Performs load action
 */
public class LoadService {
    /**
     * Create new LoadService object
     */
    public LoadService() {}

    /**
     * Clear all data from database, then loads the posted user, person, and event data into database
     * @param request Load request decoded from json; contains arrays of users, persons, and events
     * @return the result of the load request
     */
    LoadResult load(LoadRequest request) {
        return null;
    }
}
