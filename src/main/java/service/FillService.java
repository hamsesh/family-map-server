package service;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDAO;
import dao.UserDAO;
import json.DecodeException;
import json.Decoder;
import model.Location;
import model.Person;
import model.User;
import request.FillRequest;
import request.RequestException;
import result.FillResult;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

/**
 * Performs fill action
 */
public class FillService {
    /**
     * Path to database
     */
    private final String dbPath;
    /**
     * Locations from json data file
     */
    private Location[] locations;
    /**
     * Female names from json data file
     */
    private String[] fNames;
    /**
     * Male names from json data file
     */
    private String[] mNames;
    /**
     * Surnames from json data file
     */
    private String[] sNames;
    /**
     * Random number generator for getting random data
     */
    private Random rng;

    /**
     * Create new FillService object
     */
    public FillService(String dbPath) {
        this.dbPath = dbPath;
        rng = new Random();
    }

    /**
     * Populates the server database with generated data for the specified username
     * @param request FillRequest containing username and number of generations to fill
     * @return the result of the fill request
     */
    public FillResult fill(FillRequest request) throws RequestException {
        if (request.getGenerations() < 0) {
            throw new RequestException("Error: Invalid number of generations");
        }
        try {
            Database db = new Database();
            Connection conn = db.open(dbPath);
            PersonDAO personDAO = new PersonDAO(conn);

            // Delete all persons related to user
            // Delete events on cascade
            personDAO.deletePersonsByUsername(request.getUsername());

            // Create person object for user
            UserDAO userDAO = new UserDAO(db.getConnection());
            User user = userDAO.getUserByUsername(request.getUsername());
            Person userPerson = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(),
                    user.getLastName(), user.getGender(), null, null, null);

            // Get data for generating ancestry
            parseJSONData();
            // Recursively add parents with events
            addParents(userPerson, request.getGenerations(), db);
            db.close(true);
            return new FillResult(userPerson.getPersonID(),
                    "Successfully added X persons and Y events to the database", true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            return new FillResult(null, "Error: Unable to access database!", false);
        }
        catch (IOException | DecodeException e) {
            e.printStackTrace();
            return new FillResult(null, "Error: Unable to generate random data", false);
        }
    }

    private void parseJSONData() throws IOException, DecodeException {
        Decoder jsonDecoder = new Decoder();
        String locationsJsonString = Service.parseFileToString("json" + File.separator + "locations.json");
        String fNamesJsonString = Service.parseFileToString("json" + File.separator + "fnames.json");
        String mNamesJsonString = Service.parseFileToString("json" + File.separator + "mnames.json");
        String sNamesJsonString = Service.parseFileToString("json" + File.separator + "snames.json");
        locations = jsonDecoder.parseLocations(locationsJsonString);
        fNames = jsonDecoder.parseNames(fNamesJsonString);
        mNames = jsonDecoder.parseNames(mNamesJsonString);
        sNames = jsonDecoder.parseNames(sNamesJsonString);
    }

    /**
     * Recursive function that adds a parent
     * @param child Child of the parents
     */
    private void addParents(Person child, int generations, Database db) throws DataAccessException {
        PersonDAO personDAO = new PersonDAO(db.getConnection());
        if (generations == 0) {
            personDAO.insert(child);
            return;
        }
        String motherID = UUID.randomUUID().toString();
        String fatherID = UUID.randomUUID().toString();
        child.setMotherID(motherID);
        child.setFatherID(fatherID);
        String fName = fNames[rng.nextInt(fNames.length)];
        String maidenName = sNames[rng.nextInt(sNames.length)];
        // Create mother
        Person mother = new Person(motherID, child.getAssociatedUsername(), fName,
                maidenName, "f", null, null, fatherID);

        //Create father
        String mName = mNames[rng.nextInt(mNames.length)];
        Person father = new Person(fatherID, child.getAssociatedUsername(), mName, child.getLastName(), "m",
                null, null, motherID);

        int fatherGenerations = --generations;
        personDAO.insert(child);
        addParents(mother, generations, db);
        addParents(father, fatherGenerations, db);
    }
}
