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
    private String gender;
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
                  String gender, String fatherID, String motherID, String spouseID) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
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
        if (o.getClass() == Person.class) {
            Person oPerson = (Person) o;
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    ((getFatherID() == null && oPerson.getFatherID() == null) ||
                            oPerson.getFatherID().equals(getFatherID())) &&
                    ((getMotherID() == null && oPerson.getMotherID() == null) ||
                            oPerson.getMotherID().equals(getMotherID())) &&
                    ((getSpouseID() == null && oPerson.getSpouseID() == null) ||
                            oPerson.getSpouseID().equals(getSpouseID()));
        }
        else {
            return false;
        }
    }
}
