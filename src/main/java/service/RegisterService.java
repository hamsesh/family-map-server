package service;

import dao.*;
import model.AuthToken;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Performs register action
 */
public class RegisterService extends Service {

    /**
     * Create new RegisterService object
     * @param dbPath Path to database
     */
    public RegisterService(String dbPath) {
        super(dbPath);
    }

    /**
     * Register user based on RegisterRequest
     * @param request Register request decoded from json
     * @return the result of request
     */
    public RegisterResult register(RegisterRequest request) throws DataAccessException, SQLException {
        String token = generateAuthToken();
        String personID = UUID.randomUUID().toString();
        Database db = new Database();
        try {
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
                if (!db.isClosed()) {
                    db.close(false);
                }
            return new RegisterResult(null, null, null, false,
                    "Error: " + e.getMessage());
        }
        return new RegisterResult(token, request.getUsername(), personID, true, null);
    }
}
