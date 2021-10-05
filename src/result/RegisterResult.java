package result;

import model.Person;

/**
 * Result of register request
 */
public class RegisterResult {
    /**
     * Generated authToken
     */
    private String authToken;
    /**
     * User's username
     */
    private String username;
    /**
     * User's unique personID
     */
    private String personID;
    /**
     * User's new person object
     */
    private Person person;
    /**
     * Status of request
     */
    boolean success;
    /**
     * Message generated on error
     */
    private String errorMessage;

    /**
     * Create new RegisterResult and initialize data members
     */
    public RegisterResult() {
        authToken = null;
        username = null;
        personID = null;
        person = null;
        success = false;
        errorMessage = null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
