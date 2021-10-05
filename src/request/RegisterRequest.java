package request;

/**
 * Request to server to register user
 */
public class RegisterRequest {
    /**
     * Username to register
     */
    private String username;
    /**
     * Associated password
     */
    private String password;
    /**
     * Associated email
     */
    private String email;
    /**
     * User's first name
     */
    private String firstName;
    /**
     * User's last name
     */
    private String lastName;
    /**
     * User's gender (m or f)
     */
    private char gender;

    /**
     * Create new RegisterRequest and initialize data members
     */
    public RegisterRequest() {
        username = null;
        password = null;
        email = null;
        firstName = null;
        lastName = null;
        gender = '\0';
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws RequestException {
        if (email == null || email.isEmpty()) {
            throw new RequestException("Email is empty!");
        }
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws RequestException {
        if (firstName == null || firstName.isEmpty()) {
            throw new RequestException("First name is empty!");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws RequestException {
        if (lastName == null || lastName.isEmpty()) {
            throw new RequestException("Last name is empty!");
        }
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) throws RequestException {
        if (gender == '\0') {
            throw new RequestException("Gender is empty!");
        }
        else if (gender != 'm' && gender != 'f') {
            throw new RequestException("Invalid gender");
        }
        this.gender = gender;
    }
}
