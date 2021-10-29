package service;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import result.LoadResult;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Performs load action
 */
public class LoadService extends Service {
    /**
     * Create new LoadService object
     */
    public LoadService(String dbPath) {
        super(dbPath);
    }

    /**
     * Clear all data from database, then loads the posted user, person, and event data into database
     * @param request Load request decoded from json; contains arrays of users, persons, and events
     * @return the result of the load request
     */
    public LoadResult load(LoadRequest request) throws DataAccessException, SQLException {
        Database db = new Database();
        if (!request.isValidRequest()) {
            return new LoadResult("Error: Missing data in load request", false);
        }
        try {
            Connection conn = db.open(dbPath);
            UserDAO userDAO = new UserDAO(conn);
            PersonDAO personDAO = new PersonDAO(conn);
            EventDAO eventDAO = new EventDAO(conn);
            int numUsers = 0;
            int numPersons = 0;
            int numEvents = 0;

            for (User user : request.getUsers()) {
                try {
                    userDAO.insert(user);
                    numUsers++;
                }
                catch (DataAccessException e) {
                    System.out.printf("Warning: Unable to insert user: %s%n", user.getUsername());
                }
            }
            for (Person person : request.getPersons()) {
                try {
                    personDAO.insert(person);
                    numPersons++;
                }
                catch (DataAccessException e) {
                    System.out.printf("Warning: Unable to insert person: %s %s%n",
                            person.getFirstName(), person.getLastName());
                }
            }
            for (Event event : request.getEvents()) {
                try {
                    eventDAO.insert(event);
                    numEvents++;
                }
                catch (DataAccessException e) {
                    System.out.printf("Warning: Unable to insert event \"%s\" for user \"%s\"%n",
                            event.getEventType(), event.getUsername());
                }
            }
            db.close(true);
            return new LoadResult("Successfully added " + numUsers + " users, " +
                    numPersons + " persons, and " + numEvents + " events to the database.", true);
        }
        catch (DataAccessException e) {
            if (!db.isClosed()) {
                db.close(false);
            }
            return new LoadResult("Error: Unable to access database", false);
        }
    }
}
