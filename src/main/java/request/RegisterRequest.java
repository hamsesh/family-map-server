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
    private String gender;

    /**
     * Create new RegisterRequest with the following data members
     * @param username user's username
     * @param password user's password
     * @param email user's email
     * @param firstName user's first name
     * @param lastName user's last name
     * @param gender user's gender
     */
    public RegisterRequest(String username, String password, String email, String firstName,
                           String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) throws RequestException {
        if (gender == null) {
            throw new RequestException("Gender is empty!");
        }
        else if (!gender.equals("m") && !gender.equals("f")) {
            throw new RequestException("Invalid gender");
        }
        this.gender = gender;
    }

    /**
     * Tests to see if all entries are not null/empty and valid
     * @return Whether this register request is valid
     */
    public boolean isValidRequest() {
        return this.username != null && this.password != null && this.firstName != null && this.lastName != null &&
                this.email != null && this.gender != null && (this.gender.equals("m") || this.gender.equals("f")) &&
                !this.username.isEmpty() && !this.password.isEmpty() && !this.firstName.isEmpty() &&
                !this.lastName.isEmpty() && !this.email.isEmpty();
    }
}
