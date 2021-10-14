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
                System.out.print(data);

                Decoder jsonDecoder = new Decoder();
                RegisterRequest request;
                request = jsonDecoder.decodeRegister(data);

                if (!request.isValidRequest()) {
                    throw new IOException("Request not valid");
                }
                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request);

                Encoder jsonEncoder = new Encoder();
                String resultData;
                resultData = jsonEncoder.encodeRegister(result);
                writeResponseBody(exchange.getResponseBody(), resultData);
            }
        }
        catch (EncodeException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
        }
        catch (IOException | DecodeException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            e.printStackTrace();
        }
        exchange.getRequestBody().close();
    }
}
