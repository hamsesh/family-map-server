package service;

import result.FillResult;

/**
 * Performs fill action
 */
public class FillService {
    /**
     * Create new FillService object
     */
    public FillService() {}

    /**
     * Populates the server database with generated data for the specified username
     * @param username  Username of queried user
     * @param generations (Optional) Number of generations of ancestors to be generated
     * @return the result of the fill request
     */
    FillResult fill(String username, int... generations) {
        int gens = (generations.length >= 1) ? generations[0] : 0;
        return null;
    }
}
