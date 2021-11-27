package model;

import java.util.UUID;

/**
 * An event in a person's life, containing type of event, location, year, and associated user
 */
public class Event {
    /**
     * Unique ID of event
     */
    private String eventID;
    /**
     * Associated username
     */
    private String associatedUsername;
    /**
     * Associated personID
     */
    private String personID;
    /**
     * Location of event in latitude
     */
    private float latitude;
    /**
     * Location of event in longitude
     */
    private float longitude;
    /**
     * Location of event by country
     */
    private String country;
    /**
     * Location of event by city
     */
    private String city;
    /**
     * Type of event (birth, death, marriage, etc.)
     */
    private String eventType;
    /**
     * Year the event took place
     */
    private int year;

    /**
     * Create event with given params
     * @param eventID unique ID of event
     * @param username associated username
     * @param personID associated personID
     * @param latitude location of event in latitude
     * @param longitude location of event in longitude
     * @param country location of event by country
     * @param city location of event by city
     * @param eventType type of event (birth, death, marriage, etc.)
     * @param year year the event took place
     */
    public Event(String eventID, String username, String personID, float latitude, float longitude,
                 String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = username;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getUsername() {
        return associatedUsername;
    }

    public void setUsername(String username) {
        this.associatedUsername = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }

    /**
     * Check if object is equal to this event
     * @param o Object to compare
     * @return true if object has identical data to this event, else false
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null)
            return false;
        if (o.getClass() == Event.class) {
            Event oEvent = (Event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getUsername().equals(getUsername()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        }
        else {
            return false;
        }
    }

    /**
     * Create hash code for use in hash map
     * @return generated hash code
     */
    @Override
    public int hashCode() {
        return UUID.fromString(this.eventID).hashCode();
    }
}