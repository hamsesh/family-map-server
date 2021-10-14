package service;

import dao.DataAccessException;
import dao.Database;
import dao.UserDAO;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

/**
 * Performs register action
 */
public class RegisterService extends Service {
    /**
     * Create new RegisterService object
     */
    public RegisterService() {}

    /**
     * Register user based on RegisterRequest
     * @param request Register request decoded from json
     * @return the result of request
     */
    public RegisterResult register(RegisterRequest request) throws DataAccessException {
        Database db = new Database();
        UserDAO userDAO = new UserDAO(db.open(DB_PATH));
        User newUser = new User(request.getUsername(), request.getPassword(), request.getEmail(),
                request.getFirstName(), request.getLastName(), request.getGender(), "00000");
        return null;
    }
}
