package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import json.*;
import model.AuthToken;
import request.FillRequest;
import request.RequestException;
import result.FillResult;
import result.PersonResult;
import service.FillService;
import service.PersonService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles person requests
 */
public class PersonHandler extends Handler implements HttpHandler {
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
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                System.out.println("Person request received");
                Headers reqHeaders = exchange.getRequestHeaders();
                if (!reqHeaders.containsKey("Authorization")) {
                    throw new IOException("Error: Missing auth token");
                }
                String token = reqHeaders.getFirst("Authorization");
                PersonService personService = new PersonService(DB_PATH);
                PersonResult personResult = personService.person(token);

                Encoder jsonEncoder = new Encoder();
                String jsonData = jsonEncoder.encodePerson(personResult);
                exchange.getRequestBody().close();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonData.length());
                writeResponseBody(exchange.getResponseBody(), jsonData);
                exchange.getResponseBody().close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getRequestBody().close();
                exchange.getResponseBody().close();
                throw new IOException("Error: Invalid HTTP Request");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String parseAuthToken(String uri) throws RequestException {
        Scanner scnr = new Scanner(uri).useDelimiter("/");
        ArrayList<String> params = new ArrayList<>();
        while (scnr.hasNext()) {
            params.add(scnr.next());
        }
        if (params.size() > 2) {
            throw new RequestException("Error: Too many parameters in URI");
        }
        else if (params.size() < 2) {
            throw new RequestException("Error: Missing auth token");
        }
        return params.get(1);
    }
}
