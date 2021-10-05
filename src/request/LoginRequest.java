package request;

import result.LoginResult;

/**
 * Request to server to login user
 */
public class LoginRequest {
    /**
     * Login username
     */
    private String username;
    /**
     * Login password
     */
    private String password;

    /**
     * Create new LoginRequest and initialize data members
     */
    public LoginRequest() {
        username = null;
        password = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws RequestException {
        if (username == null || username.isEmpty()) {
            throw new RequestException("Username is empty!");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws RequestException {
        if (password == null || password.isEmpty()) {
            throw new RequestException("Password is empty!");
        }
        this.password = password;
    }
}
