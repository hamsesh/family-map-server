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
    private char gender;
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
     * Status of request
     */
    private boolean success;
    /**
     * Message generated on error
     */
    private String errorMessage;

    /**
     * Create new PersonIDResult and initialize data members
     */
    public PersonIDResult() {
        associatedUsername = null;
        personID = null;
        firstName = null;
        lastName = null;
        gender = '\0';
        fatherID = null;
        motherID = null;
        spouseID = null;
        success = false;
        errorMessage = null;
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

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
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
