package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;

import java.io.IOException;

/**
 * Handles login requests
 */
public class LoginHandler implements HttpHandler {
    /**
     * Create new LoginHandler object
     */
    public LoginHandler() {}

    /**
     * Handle login request
     * @param exchange HTTP exchange
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
