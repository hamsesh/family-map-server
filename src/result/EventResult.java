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
     * Status of request
     */
    private boolean success;
    /**
     * Message generated on error
     */
    private String errorMessage;

    /**
     * Create new EventResult and initialize data members
     */
    public EventResult() {
        data = null;
        success = false;
        errorMessage = null;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
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
