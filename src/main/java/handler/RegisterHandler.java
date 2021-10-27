package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import json.*;
import request.RegisterRequest;
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
                    throw new IOException("Request not valid");
                }
                RegisterService service = new RegisterService(DB_PATH);
                RegisterResult result = service.register(request);
                System.out.printf("Register process complete. Status: %s", result.isSuccess() ? "Success" : "Failure");
                if (!result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    //FIXME: specify problem
                    exchange.getRequestBody().close();
                    exchange.getResponseBody().close();
                    return;
                }

                Encoder jsonEncoder = new Encoder();
                String resultData;
                resultData = jsonEncoder.encodeRegister(result);
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
            exchange.getResponseBody().close();
        }
        catch (IOException | DecodeException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            e.printStackTrace();
            exchange.getRequestBody().close();
        }
    }
}
