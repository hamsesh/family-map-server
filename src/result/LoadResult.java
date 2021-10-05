package result;

/**
 * Result of load request
 */
public class LoadResult {
    /**
     * Message from request
     */
    private String message;
    /**
     * Request status
     */
    private boolean success;

    /**
     * Create new LoadResult and initialize data members
     */
    public LoadResult() {
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
