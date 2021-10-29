package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;
import json.Encoder;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;

import java.util.UUID;

/**
 * Performs login action
 */
public class LoginService extends Service {

    public LoginService(String dbPath) {
        super(dbPath);
    }

    /**
     * Login user
     * @param request Login request decoded from json
     * @return the result of the login request
     */
    public LoginResult login(LoginRequest request) throws DataAccessException {
        Database db = new Database();
        UserDAO userDAO = new UserDAO(db.open(dbPath));
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
        AuthToken newAuthToken;
        User foundUser;
        try {
            foundUser = userDAO.getUserByUsername(request.getUsername());
        }
        catch (DataAccessException e) {
            foundUser = null;
        }
        if (foundUser == null || !foundUser.getPassword().equals(request.getPassword())) {
            db.close(false);
            return new LoginResult(null, null, null,
                    "Error: Unable to validate user", false);
        }
        String newToken = UUID.randomUUID().toString();
        newAuthToken = new AuthToken(newToken, request.getUsername());
        authTokenDAO.insert(newAuthToken);
        db.close(true);

        return new LoginResult(newToken, foundUser.getUsername(),
                foundUser.getPersonID(), null, true);
    }
}
