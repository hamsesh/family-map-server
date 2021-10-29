package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import model.AuthToken;
import model.Person;
import result.PersonResult;

/**
 * Performs person action
 */
public class PersonService extends Service {
    /**
     * Create new PersonService object
     */
    public PersonService(String dbPath) {
        super(dbPath);
    }

    /**
     * Get all persons associated with current user
     * @param token Authentication token for current user
     * @return the result of request containing array of persons
     */
    public PersonResult person(String token) throws DataAccessException {
        Database db = new Database();
        try {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.open(dbPath));
            String username = authTokenDAO.validate(token);
            if (username == null) {
                return new PersonResult(null, "Error: Unable to authenticate user", false);
            }

            PersonDAO personDAO = new PersonDAO(db.getConnection());
            Person[] persons = personDAO.getAllPersonsByUsername(username);
            if (persons == null) {
                return new PersonResult(null, "Error: Unable to find persons", false);
            }
            return new PersonResult(persons, null, true);
        }
        catch (DataAccessException e) {
            return new PersonResult(null, "Error: Unable to authenticate user", false);
        }
        finally {
            db.close(false);
        }
    }
}
