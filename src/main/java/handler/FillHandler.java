package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;

import java.io.IOException;

/**
 * Handles fill requests
 */
public class FillHandler implements HttpHandler {
    /**
     * Create new FillHandler object
     */
    public FillHandler() {}

    /**
     * Handle fill request
     * @param exchange HTTP exchange containing username and optionally # generations
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }
}
