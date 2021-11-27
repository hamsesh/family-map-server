package result;

/**
 * Result of personID request
 */
public class PersonIDResult {
    /**
     * Username associated to person
     */
    private String associatedUsername;
    /**
     * Unique person id
     */
    private String personID;
    /**
     * Person's first name
     */
    private String firstName;
    /**
     * Person's last name
     */
    private String lastName;
    /**
     * Person's gender (m or f)
     */
    private String gender;
    /**
     * Associated fatherID (may be null)
     */
    private String fatherID;
    /**
     * Associated motherID (may be null)
     */
    private String motherID;
    /**
     * Associated spouseID (may be null)
     */
    private String spouseID;
    /**
     * Message generated on error
     */
    private String message;
    /**
     * Status of request
     */
    private boolean success;

    /**
     * Create new PersonIDResult with the following data members
     * @param associatedUsername Username associated to the person
     * @param personID The person's ID
     * @param firstName The person's first name
     * @param lastName The person's last name
     * @param gender The person's gender
     * @param fatherID The person's fatherID (may be null)
     * @param motherID The person's motherID (may be null)
     * @param spouseID The person's spouseID (may be null)
     * @param message Message generated on error
     * @param success Status of request
     */
    public PersonIDResult(String associatedUsername, String personID, String firstName, String lastName,
                          String gender, String fatherID, String motherID, String spouseID,
                          String message, boolean success) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.message = message;
        this.success = success;
    }

    public PersonIDResult(String message, boolean success) {
        this.associatedUsername = null;
        this.personID = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
        this.message = message;
        this.success = success;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
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
