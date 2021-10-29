package json;

import com.google.gson.*;
import model.Location;
import request.*;

import java.text.Normalizer;

/**
 * Decodes json strings into java object requests
 */
public class Decoder {
    /**
     * Create new Decoder object
     */
    public Decoder() {}

    /**
     * Decode given json string into LoadRequest object
     * @param jsonString json file in String
     * @return LoadRequest object generated from json
     * @throws DecodeException on invalid json
     */
    public LoadRequest decodeLoad(String jsonString) throws DecodeException {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonString, LoadRequest.class);
        }
        catch (JsonSyntaxException e) {
            throw new DecodeException(e.getMessage());
        }
    }

    /**
     * Decode given json string into LoginRequest object
     * @param jsonString json file in String
     * @return LoginRequest object generated from json
     * @throws DecodeException on invalid json
     */
    public LoginRequest decodeLogin(String jsonString) throws DecodeException {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonString, LoginRequest.class);
        }
        catch (JsonSyntaxException e) {
            throw new DecodeException(e.getMessage());
        }
    }

    /**
     * Decode given json string into RegisterRequest object
     * @param jsonString json file in String
     * @return RegisterRequest object generated from json
     * @throws DecodeException on invalid json
     */
    public RegisterRequest decodeRegister(String jsonString) throws DecodeException {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonString, RegisterRequest.class);
        }
        catch (JsonSyntaxException e) {
            throw new DecodeException(e.getMessage());
        }
    }

    public String[] parseNames(String jsonString) throws DecodeException {
        Gson gson = new Gson();
        try {
            NameData names = gson.fromJson(jsonString, NameData.class);
            return names.data;
        }
        catch (JsonSyntaxException e) {
            throw new DecodeException(e.getMessage());
        }
    }

    public Location[] parseLocations(String jsonString) throws DecodeException {
        String decomposed = Normalizer.normalize(jsonString, Normalizer.Form.NFC);
        Gson gson = new Gson();
        try {
            LocationData locations = gson.fromJson(decomposed, LocationData.class);
            return locations.data;
        }
        catch (JsonSyntaxException e) {
            throw new DecodeException(e.getMessage());
        }
    }
}

class LocationData {
    public Location[] data;
}

class NameData {
    public String[] data;
}
