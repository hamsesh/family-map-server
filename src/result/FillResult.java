package result;

/**
 * Result of fill request
 */
public class FillResult {
    /**
     * Message from request
     */
    private String message;
    /**
     * Request status
     */
    private boolean success;

    /**
     * Create new FillResult and initialize data members
     */
    public FillResult() {
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
