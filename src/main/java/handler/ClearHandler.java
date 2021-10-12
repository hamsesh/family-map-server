package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Handles clear requests
 */
public class ClearHandler implements HttpHandler {
    /**
     * Create new ClearHandler object
     */
    public ClearHandler() {}

    /**
     * Handle clear request
     * @param exchange HTTP exchange
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("delete")) {
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
