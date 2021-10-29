package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

/**
 * Handles requests for webpage
 */
public class FileHandler implements HttpHandler {
    /**
     * Handles webpage requests
     * @param exchange HTTP exchange
     * @throws IOException on invalid IO
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                String urlPath = exchange.getRequestURI().toString();
                if (urlPath == null || urlPath.equals("/")) urlPath = "/index.html";
                urlPath = "web" + urlPath;
                File file = new File(urlPath);
                OutputStream respBody;
                if (!file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    File notFoundFile = new File("web/HTML/404.html");
                    respBody = exchange.getResponseBody();
                    Files.copy(notFoundFile.toPath(), respBody);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                    success = true;
                }
                respBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
            }

            if (!success) {
                throw new IOException();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(500, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
