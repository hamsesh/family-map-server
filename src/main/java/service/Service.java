package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;

public class Service {
    /**
     * Random number generator for auth tokens
     */
    protected static final SecureRandom secureRandom = new SecureRandom();
    /**
     * Encoder for auth tokens
     */
    protected static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
    /**
     * Path to database
     */
    protected final String dbPath;

    public Service(String dbPath) {
        this.dbPath = dbPath;
    }

    protected static String generateAuthToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String parseFileToString(String path) throws IOException {
            return new String(Files.readAllBytes(Paths.get(path)));
    }
}
