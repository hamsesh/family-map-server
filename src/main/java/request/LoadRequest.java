package request;

import model.Event;
import model.Person;
import model.User;

/**
 * Request to server to load data
 */
public class LoadRequest {
    /**
     * Array of user objects to load
     */
    private User[] users;
    /**
     * Array of person objects to load
     */
    private Person[] persons;
    /**
     * Array of event objects to load
     */
    private Event[] events;

    /**
     * Create new LoadRequest object with the following data members
     * @param users Array of user objects to load
     * @param persons Array of person objects to load
     * @param events Array of event objects to load
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) throws RequestException {
        if (users == null || users.length == 0) {
            throw new RequestException("No users found");
        }
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) throws RequestException {
        if (persons == null || persons.length == 0) {
            throw new RequestException("No people found");
        }
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) throws RequestException {
        if (events == null || events.length == 0) {
            throw new RequestException("No events found");
        }
        this.events = events;
    }

    public boolean isValidRequest() {
        return this.users != null && this.persons != null && this.events != null;
    }
}
