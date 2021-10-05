package result;

import model.Person;

/**
 * Result of person request
 */
public class PersonResult {
    /**
     * Array of persons
     */
    private Person[] data;
    /**
     * Status of request
     */
    private boolean success;
    /**
     * Message generated on error
     */
    private String errorMessage;

    /**
     * Create new PersonResult and initialize data members
     */
    public PersonResult() {
        data = null;
        success = false;
        errorMessage = null;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
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
