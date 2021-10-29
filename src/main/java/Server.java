import com.sun.net.httpserver.HttpServer;
import handler.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    static final int MAX_WAITING_CONNECTIONS = 500;
    private HttpServer server;

    public void run(String portNumber) {

        System.out.println("Initializing HTTP Server");

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        server.setExecutor(null);

        // Creating and installing HTTP handlers
        System.out.println("Creating contexts");

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new FileHandler());

        System.out.println("Starting server...");

        server.start();

        System.out.printf("Server now listening on port %s%n", portNumber);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Server.java <port number>");
        }
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
