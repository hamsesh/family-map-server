package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;
import model.AuthToken;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Handles personID requests
 */
public class PersonIDHandler implements HttpHandler {
    /**
     * Create new PersonIDHandler object
     */
    public PersonIDHandler() {}

    /**
     * Handle clear request
     * @param exchange HTTP exchange containing AuthToken of current logged-in user and ID of person to get
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                // TODO: clear!

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                success = true;
            }
            if (!success) {
                throw new IOException("Invalid HTTP Request");
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
