package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
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
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                System.out.println("Register request recieved: ");
                String data = readInputStream(exchange.getRequestBody());
                System.out.print(data);

                Decoder jsonDecoder = new Decoder();
                RegisterRequest request;
                try {
                    request = jsonDecoder.decodeRegister(data);
                }
                catch (DecodeException e) {
                    throw new HandlerException("Unable to decode request");
                }

                if (!request.isValidRequest()) {
                    throw new HandlerException("Request not valid");
                }
                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request);
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
        catch (HandlerException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getRequestBody().close();
            e.printStackTrace();
        }
    }
}
