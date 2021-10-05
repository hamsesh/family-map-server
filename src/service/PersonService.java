package service;

import model.AuthToken;
import result.PersonResult;

/**
 * Performs person action
 */
public class PersonService {
    /**
     * Create new PersonService object
     */
    public PersonService() {}

    /**
     * Get all persons associated with current user
     * @param token Authentication token for current user
     * @return the result of request containing array of persons
     */
    PersonResult person(AuthToken token) {
        return null;
    }
}
