package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;
import model.AuthToken;

import java.io.IOException;

/**
 * Handles person requests
 */
public class PersonHandler implements HttpHandler {
    /**
     * Create new PersonHandler object
     */
    public PersonHandler() {}

    /**
     * Handle person request
     * @param exchange HTTP exchange containing AuthToken of current logged-in user
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
