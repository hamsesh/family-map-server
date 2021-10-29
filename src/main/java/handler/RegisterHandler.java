package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import json.*;
import request.FillRequest;
import request.RegisterRequest;
import request.RequestException;
import result.FillResult;
import service.FillService;
import service.RegisterService;
import result.RegisterResult;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

/**
 * Handles register requests
 */
public class RegisterHandler extends Handler implements HttpHandler {
    /**
     * Create new RegisterHandler object
     */
    public RegisterHandler() {}

    /**
     * Handle clear request
     * @param exchange HTTP exchange
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                System.out.println("Register request recieved: ");
                String data = readInputStream(exchange.getRequestBody());

                Decoder jsonDecoder = new Decoder();
                System.out.println("Decoding request...");
                RegisterRequest request = jsonDecoder.decodeRegister(data);
                System.out.println("Finished decoding");

                if (!request.isValidRequest()) {
                    System.out.println("Invalid register request");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getRequestBody().close();
                    throw new IOException("Error: Register request not valid");
                }
                System.out.println("Valid register request");
                RegisterService registerService = new RegisterService(DB_PATH);
                RegisterResult registerResult = registerService.register(request);
                System.out.printf("Register process complete. Status: %s%n",
                        registerResult.isSuccess() ? "Success" : "Failure");

                if (registerResult.isSuccess()) {
                    FillRequest fillRequest = new FillRequest(registerResult.getUsername(), 4);
                    if (!fillRequest.isValidRequest()) {
                        exchange.sendResponseHeaders(500, 0);
                        exchange.getRequestBody().close();
                        exchange.getResponseBody().close();
                        throw new IOException("Error: Fill request not valid");
                    }
                    FillService fillService = new FillService(DB_PATH);
                    FillResult fillResult = fillService.fill(fillRequest);
                    System.out.printf("Fill process complete. Status: %s%n",
                            fillResult.isSuccess() ? "Success" : "Failure");

                    if (!fillResult.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        exchange.getRequestBody().close();
                        exchange.getResponseBody().close();
                        throw new IOException("Error: " + fillResult.getMessage());
                    }
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                Encoder jsonEncoder = new Encoder();
                String jsonData = jsonEncoder.encodeRegister(registerResult);
                exchange.getRequestBody().close();
                writeResponseBody(exchange.getResponseBody(), jsonData);
                exchange.getResponseBody().close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                exchange.getRequestBody().close();
                throw new IOException("Error: Invalid HTTP Request");
            }
        }
        catch (EncodeException | DataAccessException | SQLException e) {
            exchange.sendResponseHeaders(500, 0);
            e.printStackTrace();
            exchange.getRequestBody().close();
            exchange.getResponseBody().close();
        }
        catch (DecodeException | RequestException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            e.printStackTrace();
            exchange.getRequestBody().close();
            exchange.getResponseBody().close();
        }
    }
}
