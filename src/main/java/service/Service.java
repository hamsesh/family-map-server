package service;

import java.io.File;
import java.security.SecureRandom;
import java.util.Base64;

public class Service {
    protected static final SecureRandom secureRandom = new SecureRandom();
    protected static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    protected static String generateAuthToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
