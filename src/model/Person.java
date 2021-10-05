package model;

/**
 * Person in the family map
 */
public class Person {
    /**
     * Unique personID
     */
    private String personID;
    /**
     * Username associated with this person
     */
    private String associatedUsername;
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
     * FatherID associated with this person (may be null)
     */
    private String fatherID;
    /**
     * MotherID associated with this person (may be null)
     */
    private String motherID;
    /**
     * SpouseID associated with this person (may be null)
     */
    private String spouseID;

    /**
     * Create new Person object with given params
     * @param personID unique personID
     * @param associatedUsername username associated with this person
     * @param firstName person's first name
     * @param lastName person's last name
     * @param gender person's gender (m or f)
     * @param fatherID fatherID associated with this person (may be null)
     * @param motherID motherID associated with this person (may be null)
     * @param spouseID spouseID associated with this person (may be null)
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName,
                  char gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    /**
     * Check if object is equal to this person
     * @param o Object to compare
     * @return true if object has identical data to this person, else false
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o instanceof Person) {
            Person oPerson = (Person) o;
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender() == getGender() &&
                    oPerson.getFatherID().equals(getFatherID()) &&
                    oPerson.getMotherID().equals(getMotherID()) &&
                    oPerson.getSpouseID().equals(getSpouseID());

        } else {
            return false;
        }
    }
}
