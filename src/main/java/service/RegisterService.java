package service;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * Performs register action
 */
public class RegisterService extends Service {
    /**
     * Path to database
     */
    private final String dbPath;

    /**
     * Create new RegisterService object
     * @param dbPath Path to database
     */
    public RegisterService(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * Register user based on RegisterRequest
     * @param request Register request decoded from json
     * @return the result of request
     */
    public RegisterResult register(RegisterRequest request) {
        String token = generateAuthToken();
        String personID = UUID.randomUUID().toString();
        try {
            Database db = new Database();
            Connection conn = db.open(dbPath);
            UserDAO userDAO = new UserDAO(conn);
            User newUser = new User(request.getUsername(), request.getPassword(), request.getEmail(),
                    request.getFirstName(), request.getLastName(), request.getGender(), personID);
            userDAO.insert(newUser);

            AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
            AuthToken authToken = new AuthToken(token, request.getUsername());
            authTokenDAO.insert(authToken);

            db.close(true);
        }
        catch (DataAccessException e) {
            return new RegisterResult(null, null, null, false, e.getMessage());
        }
        return new RegisterResult(token, request.getUsername(), personID, true, null);
    }
}
