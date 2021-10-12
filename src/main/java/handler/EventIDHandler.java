package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;
import model.AuthToken;

import java.io.IOException;

/**
 * Handles eventID requests
 */
public class EventIDHandler implements HttpHandler {
    /**
     * Create new EventIDHandler
     */
    public EventIDHandler() {}

    /**
     * Handle eventID request
     * @param exchange HTTP exchange
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
