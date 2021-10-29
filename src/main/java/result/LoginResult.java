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
     * Message generated on error
     */
    private String message;
    /**
     * Status of request
     */
    private boolean success;

    /**
     *
     * @param authToken AuthToken genereated
     * @param username Associated username
     * @param personID Associated personID
     * @param success Success status
     * @param errorMessage Generated error message
     */
    public LoginResult(String authToken, String username, String personID, String errorMessage, boolean success) {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
        this.message = errorMessage;
        this.success = success;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
