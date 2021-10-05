package result;

/**
 * Result of login request
 */
public class LoginResult {
    /**
     * Generated authToken
     */
    private String authToken;
    /**
     * User's username
     */
    private String username;
    /**
     * User's personID
     */
    private String personID;
    /**
     * Status of request
     */
    private boolean success;
    /**
     * Message generated on error
     */
    private String errorMessage;

    /**
     * Create new LoginResult and initialize data members
     */
    public LoginResult() {
        authToken = null;
        username = null;
        personID = null;
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
