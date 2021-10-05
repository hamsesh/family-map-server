package handler;

import json.*;
import model.AuthToken;

/**
 * Handles event requests
 */
public class EventHandler {
    /**
     * Create new EventHanlder object
     */
    public EventHandler() {}

    /**
     * Handle event request
     * @param token AuthToken of current logged-in user
     * @param jsonRequest json string received from client
     * @return json string of result
     */
    public String handleEvent(AuthToken token, String jsonRequest) {
        return null;
    }
}
