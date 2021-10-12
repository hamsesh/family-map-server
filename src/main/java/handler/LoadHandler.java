package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;

import java.io.IOException;

/**
 * Handles load requests
 */
public class LoadHandler implements HttpHandler {
    /**
     * Create new LoadHandler object
     */
    public LoadHandler() {}

    /**
     * Handle load request
     * @param exchange HTTP exchange
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
