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
     * Message generated on error
     */
    private String message;
    /**
     * Status of request
     */
    private boolean success;

    /**
     * @param data Array of persons
     * @param message Message generated on error
     * @param success Status of request
     */
    public PersonResult(Person[] data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
