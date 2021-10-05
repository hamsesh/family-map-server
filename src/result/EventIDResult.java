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
     * Status of request
     */
    private boolean success;
    /**
     * Message generated on error
     */
    private String errorMessage;

    /**
     * Create new EventIDResult and initialize data members
     */
    public EventIDResult() {
        username = null;
        eventID = null;
        latitude = 0f;
        longitude = 0f;
        country = null;
        city = null;
        eventType = null;
        year = 0;
        success = false;
        errorMessage = null;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
