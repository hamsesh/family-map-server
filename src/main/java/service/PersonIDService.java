package service;

import dao.AuthTokenDAO;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import model.Person;
import result.PersonIDResult;
import result.PersonResult;

/**
 * Performs personID action
 */
public class PersonIDService extends Service {
    /**
     * Create new PersonIDService object
     */
    public PersonIDService(String dbPath) {
        super(dbPath);
    }

    /**
     * Get person by ID
     * @param token Auth token for authentication
     * @param personID ID of person to find
     * @return the result of the personID request; may be null if person not found or if not associated with user
     */
    public PersonIDResult personID(String token, String personID) throws DataAccessException {
        Database db = new Database();
        try {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.open(dbPath));
            String username = authTokenDAO.validate(token);
            if (username == null) {
                return new PersonIDResult("Error: Unable to authenticate user", false);
            }

            PersonDAO personDAO = new PersonDAO(db.getConnection());
            Person person = personDAO.getPersonByID(personID);
            if (person == null || !person.getAssociatedUsername().equals(username)) {
                return new PersonIDResult("Error: Unable to find person with given ID", false);
            }

            return new PersonIDResult(person.getAssociatedUsername(), person.getPersonID(), person.getFirstName(),
                    person.getLastName(), person.getGender(), person.getFatherID(), person.getMotherID(),
                    person.getSpouseID(), null, true);
        }
        catch (DataAccessException e) {
            return new PersonIDResult("Error: Unable to authenticate user", false);
        }
        finally {
            db.close(false);
        }
    }
}
