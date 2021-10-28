package request;

/**
 * Request for filling database with information
 */
public class FillRequest {
    /**
     * The associated username
     */
    private final String username;
    /**
     * Number of generations to fill
     */
    private final int generations;

    /**
     * Create new FillRequest with following data members
     * @param username The associated username
     * @param generations Number of generations to fill
     */
    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public final String getUsername() {
        return this.username;
    }

    public final int getGenerations() {
        return this.generations;
    }

    public boolean isValidRequest() {
        return generations >= 0 && !username.isEmpty();
    }
}
