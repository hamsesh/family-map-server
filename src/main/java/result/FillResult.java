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

    /**
     * Create new FillResult with following data members
     * @param message Message from request
     * @param success Success status
     */
    public FillResult(String message, boolean success) {
        this.message = message;
        this.success = success;
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
