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
                System.out.printf("%s%n", data);

                Decoder jsonDecoder = new Decoder();
                RegisterRequest request;
                request = jsonDecoder.decodeRegister(data);

                if (!request.isValidRequest()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getRequestBody().close();
                    throw new IOException("Error: Register request not valid");
                }
                RegisterService registerService = new RegisterService(DB_PATH);
                RegisterResult registerResult = registerService.register(request);
                System.out.printf("Register process complete. Status: %s",
                        registerResult.isSuccess() ? "Success" : "Failure");

                if (!registerResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getRequestBody().close();
                    exchange.getResponseBody().close();
                    throw new IOException("Error: " + registerResult.getErrorMessage());
                }

                FillRequest fillRequest = new FillRequest(registerResult.getUsername(), 4);
                if (!fillRequest.isValidRequest()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                    exchange.getRequestBody().close();
                    exchange.getResponseBody().close();
                    throw new IOException("Error: Fill request not valid");
                }
                FillService fillService = new FillService(DB_PATH);
                FillResult fillResult = fillService.fill(fillRequest);
                if (!fillResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getRequestBody().close();
                    exchange.getResponseBody().close();
                    throw new IOException("Error: " + fillResult.getMessage());
                }

                Encoder jsonEncoder = new Encoder();
                String resultData;
                resultData = jsonEncoder.encodeRegister(registerResult);
                exchange.getRequestBody().close();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                writeResponseBody(exchange.getResponseBody(), resultData);
                exchange.getResponseBody().close();
            }
        }
        catch (EncodeException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
            exchange.getRequestBody().close();
            exchange.getResponseBody().close();
        }
        catch (IOException | DecodeException | RequestException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            e.printStackTrace();
            exchange.getRequestBody().close();
            exchange.getResponseBody().close();
        }
    }
}
