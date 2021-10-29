package result;

/**
 * Result of eventID request
 */
public class EventIDResult {
    /**
     * Associated username
     */
    private String username;
    /**
     * Unique eventID
     */
    private String eventID;
    /**
     * Location in latitude
     */
    private float latitude;
    /**
     * Location in longitude
     */
    private float longitude;
    /**
     * Location by country
     */
    private String country;
    /**
     * Location by city
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
     * Message generated on error
     */
    private String message;
    /**
     * Status of request
     */
    private boolean success;

    /**
     * Create new EventIDResult and initialize data members
     * @param username Associated username
     * @param eventID Event's unique ID
     * @param latitude Location in latitude
     * @param longitude Location in longitude
     * @param country Location by country
     * @param city Location by city
     * @param eventType Type of event
     * @param year Year of the event
     * @param message Message generated on error
     * @param success Status of request
     */
    public EventIDResult(String username, String eventID, float latitude, float longitude, String country,
                         String city, String eventType, int year, String message, boolean success) {
        this.username = username;
        this.eventID = eventID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.message = message;
        this.success = success;
    }

    /**
     * Create new EventIDResult and initialize data members
     * @param message Message generated on error
     * @param success Status of request
     */
    public EventIDResult(String message, boolean success) {
        username = null;
        eventID = null;
        latitude = 0f;
        longitude = 0f;
        country = null;
        city = null;
        eventType = null;
        year = 0;
        this.message = message;
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
