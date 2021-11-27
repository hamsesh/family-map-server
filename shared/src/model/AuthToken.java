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
    private final String username;

    /**
     * Create new AuthToken with given token string and userID
     * @param token AuthToken string
     * @param username corresponding userID
     */
    public AuthToken(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
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
        AuthToken authToken = (AuthToken) o;
        if (o.getClass() == AuthToken.class) {
            AuthToken oAuthToken = (AuthToken) o;
            return oAuthToken.getToken().equals(getToken()) &&
                    oAuthToken.getUsername().equals(getUsername());
        }
        else {
            return false;
        }
    }
}
