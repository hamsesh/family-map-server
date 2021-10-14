package json;

import com.google.gson.*;
import result.*;

import javax.xml.transform.Result;

/**
 * Encodes java object results into json strings
 */
public class Encoder {
    /**
     * Create new Encoder object
     */
    public Encoder() {}

    /**
     * Encode ClearResult object into json string
     * @param result ClearResult object to encode
     * @return encoded json string
     * @throws EncodeException on invalid object or gson exception
     */
    public String encodeClear(ClearResult result) throws EncodeException {
        return null;
    }

    /**
     * Encode EventIDResult object into json string
     * @param result EventIDResult object to encode
     * @return encoded json string
     * @throws EncodeException on invalid object or gson exception
     */
    public String encodeEventID(EventIDResult result) throws EncodeException {
        return null;
    }

    /**
     * Encode EventResult object into json string
     * @param result EventResult object to encode
     * @return encoded json string
     * @throws EncodeException on invalid object or gson exception
     */
    public String encodeEvent(EventResult result) throws EncodeException {
        return null;
    }

    /**
     * Encode FillResult object into json string
     * @param result FillResult object to encode
     * @return encoded json string
     * @throws EncodeException on invalid object or gson exception
     */
    public String encodeFill(FillResult result) throws EncodeException {
        return null;
    }

    /**
     * Encode LoadResult object into json string
     * @param result LoadResult object to encode
     * @return encoded json string
     * @throws EncodeException on invalid object or gson exception
     */
    public String encodeLoad(Result result) throws EncodeException {
        return null;
    }

    /**
     * Encode LoginResult object into json string
     * @param result LoginResult object to encode
     * @return encoded json string
     * @throws EncodeException on invalid object or gson exception
     */
    public String encodeLogin(LoginResult result) throws EncodeException {
        return null;
    }

    /**
     * Encode PersonIDResult object into json string
     * @param result PersonIDResult object to encode
     * @return encoded json string
     * @throws EncodeException on invalid object or gson exception
     */
    public String encodePersonID(PersonIDResult result) throws EncodeException {
        return null;
    }

    /**
     * Encode PersonResult object into json string
     * @param result PersonResult object to encode
     * @return encoded json string
     * @throws EncodeException on invalid object or gson exception
     */
    public String encodePerson(PersonResult result) throws EncodeException {
        return null;
    }

    /**
     * Encode RegisterResult object into json string
     * @param result RegisterResult object to encode
     * @return encoded json string
     * @throws EncodeException on invalid object or gson exception
     */
    public String encodeRegister(RegisterResult result) throws EncodeException {
        Gson gson = new Gson();
        try {
            return gson.toJson(result);
        }
        catch (JsonIOException e) {
            throw new EncodeException();
        }
    }
}
