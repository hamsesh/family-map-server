package result;

/**
 * Result of clear request
 */
public class ClearResult {
    /**
     * Result message
     */
    private String message;
    /**
     * Status of request
     */
    private boolean success;

    /**
     * Create new ClearResult and initialize data members
     */
    public ClearResult() {
        message = null;
        success = false;
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