package handler;

import json.*;
import model.AuthToken;

/**
 * Handles personID requests
 */
public class PersonIDHandler {
    /**
     * Create new PersonIDHandler object
     */
    public PersonIDHandler() {}

    /**
     * Handle clear request
     * @param token AuthToken of current logged-in user
     * @param personID ID of person to get
     * @return json string of result
     */
    public String handleClear(AuthToken token, String personID) {
        return null;
    }
}
