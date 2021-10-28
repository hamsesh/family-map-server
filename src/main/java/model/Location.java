package model;

/**
 * Location data model for json parsing
 */
public class Location {
    /**
     * Location's country
     */
    String country;
    /**
     * Location's city
     */
    String city;
    /**
     * Location in latitude
     */
    float latitude;
    /**
     * Location in longitude
     */
    float longitude;

    /**
     * Create new Location object
     * @param country Location's country
     * @param city Location's city
     * @param latitude Location's latitude
     * @param longitude Location's longitude
     */
    public Location(String country, String city, float latitude, float longitude) {
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
