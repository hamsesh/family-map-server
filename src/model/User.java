package model;

/**
 * User of family map server
 */
public class User {
    /**
     * Unique ID for user
     */
    private String userID;
    /**
     * User's username
     */
    private String username;
    /**
     * User's password
     */
    private String password;
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
     * Create new User with given params
     * @param userID unique ID for user
     * @param username user's username
     * @param password user's password
     * @param firstName user's first name
     * @param lastName user's last name
     * @param gender user's gender (m or f)
     */
    public User(String userID, String username, String password, String firstName,
                String lastName, char gender) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Check if object is equal to this user
     * @param o Object to compare
     * @return true if object has identical data to this user, else false
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o instanceof User) {
            User oUser = (User) o;
            return oUser.getUserID().equals(getUserID()) &&
                    oUser.getUsername().equals(getUsername()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender() == getGender();
        } else {
            return false;
        }
    }
}
