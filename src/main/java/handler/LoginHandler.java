package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import json.*;
import request.LoginRequest;
import request.RegisterRequest;
import request.RequestException;
import result.LoginResult;
import service.LoginService;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Handles login requests
 */
public class LoginHandler extends Handler implements HttpHandler {
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
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                System.out.println("Login request received");
                String data = readInputStream(exchange.getRequestBody());

                Decoder jsonDecoder = new Decoder();
                System.out.println("Decoding request...");
                LoginRequest loginRequest = jsonDecoder.decodeLogin(data);
                System.out.println("Finished decoding");

                if (!loginRequest.isValidRequest()) {
                    System.out.println("Invalid login request");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getRequestBody().close();
                    throw new IOException("Error: Login request not valid");
                }
                System.out.printf("User: %s%n", loginRequest.getUsername());
                LoginService loginService = new LoginService(DB_PATH);
                LoginResult loginResult = loginService.login(loginRequest);

                Encoder jsonEncoder = new Encoder();
                String jsonData = jsonEncoder.encodeLogin(loginResult);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, jsonData.length());
                writeResponseBody(exchange.getResponseBody(), jsonData);
                exchange.getRequestBody().close();
                exchange.getResponseBody().close();
            }
            else {
                exchange.sendResponseHeaders(500, 0);
                exchange.getRequestBody().close();
                exchange.getResponseBody().close();
                throw new IOException("Error: Invalid HTTP Request");
            }
        }
        catch (DecodeException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            throw new IOException("Error " + e.getMessage());
        }
        catch (EncodeException | DataAccessException e) {
            exchange.sendResponseHeaders(500, 0);
            throw new IOException("Error " + e.getMessage());
        }
        finally {
            exchange.getRequestBody().close();
            exchange.getResponseBody().close();
        }
    }
}
