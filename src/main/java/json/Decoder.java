package json;

import com.google.gson.*;
import model.Location;
import request.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    public LoadRequest decodeClear(String jsonString) throws DecodeException {
        return null;
    }

    /**
     * Decode given json string into LoginRequest object
     * @param jsonString json file in String
     * @return LoginRequest object generated from json
     * @throws DecodeException on invalid json
     */
    public LoginRequest decodeLogin(String jsonString) throws DecodeException {
        return null;
    }

    /**
     * Decode given json string into RegisterRequest object
     * @param jsonString json file in String
     * @return RegisterRequest object generated from json
     * @throws DecodeException on invalid json
     */
    public RegisterRequest decodeRegister(String jsonString) throws DecodeException {
        return null;
    }

    public String[] parseNames(String jsonString) throws DecodeException {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonString, String[].class);
        }
        catch (JsonSyntaxException e) {
            throw new DecodeException(e.getMessage());
        }
    }

    public Location[] parseLocations(String jsonString) throws DecodeException {
        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonString, Location[].class);
        }
        catch (JsonSyntaxException e) {
            throw new DecodeException(e.getMessage());
        }
    }
}
