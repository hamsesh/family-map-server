package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import json.*;
import request.RequestException;
import result.PersonIDResult;
import result.PersonResult;
import service.PersonIDService;
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
                String uri = exchange.getRequestURI().getPath();
                String personID = parseID(uri);

                if (personID != null) {
                    PersonIDService personIDService = new PersonIDService(DB_PATH);
                    PersonIDResult personIDResult = personIDService.personID(token, personID);

                    Encoder jsonEncoder = new Encoder();
                    String jsonData = jsonEncoder.encodePersonID(personIDResult);
                    exchange.getRequestBody().close();
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonData.length());
                    writeResponseBody(exchange.getResponseBody(), jsonData);
                    exchange.getResponseBody().close();
                }
                else {
                    // Get all persons
                    PersonService personService = new PersonService(DB_PATH);
                    PersonResult personResult = personService.person(token);

                    Encoder jsonEncoder = new Encoder();
                    String jsonData = jsonEncoder.encodePerson(personResult);
                    exchange.getRequestBody().close();
                    System.out.println("Person process complete");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonData.length());
                    writeResponseBody(exchange.getResponseBody(), jsonData);
                    exchange.getResponseBody().close();
                }
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getRequestBody().close();
                exchange.getResponseBody().close();
                throw new IOException("Error: Invalid HTTP Request");
            }
        }
        catch (EncodeException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
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
}
