package model;

/**
 * Authentication token generated at register and login; used to authenticate requests
 */
public class AuthToken {
    /**
     * AuthToken string
     */
    private final String token;
    /**
     * Corresponding userID
     */
    private final String userID;

    /**
     * Create new AuthToken with given token string and userID
     * @param token AuthToken string
     * @param userID corresponding userID
     */
    public AuthToken(String token, String userID) {
        this.token = token;
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public String getUserID() {
        return userID;
    }

    /**
     * Check if object is equal to this authToken
     * @param o Object to compare
     * @return true if object has identical data to this authToken, else false
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o instanceof AuthToken) {
            AuthToken oAuthToken = (AuthToken) o;
            return oAuthToken.getToken().equals(getToken()) &&
                    oAuthToken.getUserID().equals(getUserID());
        } else {
            return false;
        }
    }
}
