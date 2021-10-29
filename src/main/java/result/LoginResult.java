package result;

/**
 * Result of login request
 */
public class LoginResult {
    /**
     * Generated authToken
     */
    private String authtoken;
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
     * @param authtoken AuthToken genereated
     * @param username Associated username
     * @param personID Associated personID
     * @param success Success status
     * @param errorMessage Generated error message
     */
    public LoginResult(String authtoken, String username, String personID, String errorMessage, boolean success) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.message = errorMessage;
        this.success = success;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
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
