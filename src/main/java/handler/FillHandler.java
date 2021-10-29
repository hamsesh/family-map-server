package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import json.*;
import request.FillRequest;
import request.RequestException;
import result.FillResult;
import service.FillService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles fill requests
 */
public class FillHandler extends Handler implements HttpHandler {
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
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                System.out.println("Fill request received");
                String uri = exchange.getRequestURI().getPath();
                FillRequest request = parseParams(uri);
                FillService service = new FillService(DB_PATH);
                FillResult result = service.fill(request);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Encoder jsonEncoder = new Encoder();
                String jsonData = jsonEncoder.encodeFill(result);
                writeResponseBody(exchange.getResponseBody(), jsonData);
                exchange.getRequestBody().close();
                exchange.getResponseBody().close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getRequestBody().close();
                exchange.getResponseBody().close();
                throw new IOException("Error: Invalid HTTP Request");
            }
        }
        catch (EncodeException | DataAccessException | SQLException e) {
            exchange.sendResponseHeaders(500, 0);
            throw new IOException(e.getMessage());
        }
        catch (RequestException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            throw new IOException(e.getMessage());
        }
        finally {
            exchange.getRequestBody().close();
            exchange.getResponseBody().close();
        }
    }

    private FillRequest parseParams(String uri) throws RequestException {
        Scanner scnr = new Scanner(uri).useDelimiter("/");
        ArrayList<String> params = new ArrayList<>();
        while (scnr.hasNext()) {
            params.add(scnr.next());
        }
        if (params.size() > 3) {
            throw new RequestException("Error: Too many parameters in URI");
        }
        else if (params.size() < 2) {
            throw new RequestException("Error: Missing parameters");
        }
        else if (params.size() == 3) {
            try {
                int generations = Integer.parseInt(params.get(2));
                String username = params.get(1);
                return new FillRequest(username, generations);
            } catch (NumberFormatException e) {
                throw new RequestException("Error: Generations param is not a number");
            }
        }
        else {
            String username = params.get(1);
            return new FillRequest(username);
        }
    }
}
