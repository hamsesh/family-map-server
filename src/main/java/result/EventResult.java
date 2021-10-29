package result;

import model.Event;
/**
 * Result of event request
 */
public class EventResult {
    /**
     * Array of events
     */
    private Event[] data;
    /**
     * Message generated on error
     */
    private String message;
    /**
     * Status of request
     */
    private boolean success;

    /**
     * Create EventResult and initialize data members
     * @param data Array of events
     * @param message Message generated on error
     * @param success Status of request
     */
    public EventResult(Event[] data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
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
