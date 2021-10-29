package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DataAccessException;
import json.*;
import request.LoadRequest;
import result.ClearResult;
import result.LoadResult;
import service.ClearService;
import service.LoadService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

/**
 * Handles load requests
 */
public class LoadHandler extends Handler implements HttpHandler {
    /**
     * Create new LoadHandler object
     */
    public LoadHandler() {}

    /**
     * Handle load request
     * @param exchange HTTP exchange
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                System.out.println("Load request received");
                ClearService clearService = new ClearService(DB_PATH);
                ClearResult clearResult = clearService.clear();
                if (!clearResult.isSuccess()) {
                    throw new IOException("Error: Unable to clear database");
                }

                System.out.println("Load request recieved: ");
                String data = readInputStream(exchange.getRequestBody());

                Decoder jsonDecoder = new Decoder();
                System.out.println("Decoding request...");
                LoadRequest loadRequest = jsonDecoder.decodeLoad(data);
                LoadService loadService = new LoadService(DB_PATH);
                LoadResult loadResult = loadService.load(loadRequest);
                System.out.println("Finished decoding");

                Encoder jsonEncoder = new Encoder();
                String jsonData = jsonEncoder.encodeLoad(loadResult);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                writeResponseBody(exchange.getResponseBody(), jsonData);
                System.out.println("Load complete");
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
                throw new IOException("Error: Invalid HTTP Request");
            }
        }
        catch (IOException | EncodeException | DataAccessException | SQLException e) {
            exchange.sendResponseHeaders(500, 0);
            throw new IOException("Error: " + e.getMessage());
        }
        catch (DecodeException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            throw new IOException("Error: Invalid load request");
        }
        finally {
            exchange.getRequestBody().close();
            exchange.getResponseBody().close();
        }
    }
}
