package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;
import result.ClearResult;
import service.ClearService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Handles clear requests
 */
public class ClearHandler extends Handler implements HttpHandler {
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
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                ClearService service = new ClearService(DB_PATH);
                ClearResult result = service.clear();
                Encoder jsonEncoder = new Encoder();
                String jsonData = jsonEncoder.encodeClear(result);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                writeResponseBody(exchange.getResponseBody(), jsonData);
                exchange.getRequestBody().close();
                exchange.getResponseBody().close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getRequestBody().close();
                throw new IOException("Error: Invalid HTTP Request");
            }
        }
        catch (IOException | EncodeException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
