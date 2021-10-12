package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;
import model.AuthToken;

import java.io.IOException;

/**
 * Handles event requests
 */
public class EventHandler implements HttpHandler {
    /**
     * Create new EventHanlder object
     */
    public EventHandler() {}

    /**
     * Handle event request
     * @param exchange HTTP exchange
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
    }
}
