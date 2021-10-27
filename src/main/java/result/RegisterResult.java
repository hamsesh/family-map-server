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
     * Status of request
     */
    boolean success;
    /**
     * Message generated on error
     */
    private String errorMessage;

    /**
     * Create new RegisterResult with the following data members
     * @param authToken Resulting auth token
     * @param username Associated username
     * @param personID Associated person ID
     * @param success Success status
     * @param errorMessage Generated error message if not successful
     */
    public RegisterResult(String authToken, String username, String personID, boolean success,
                          String errorMessage) {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.errorMessage = errorMessage;
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
