package service;

import dao.*;
import json.DecodeException;
import json.Decoder;
import model.Event;
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
public class FillService extends Service {
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
    private final Random rng;
    /**
     * Number of persons added on fill
     */
    private int numPersonsAdded;
    /**
     * Number of events added on fill
     */
    private int numEventsAdded;

    /**
     * Create new FillService object
     */
    public FillService(String dbPath) {
        super(dbPath);
        rng = new Random();
        numPersonsAdded = 0;
        numEventsAdded = 0;
    }

    /**
     * Populates the server database with generated data for the specified username
     * @param request FillRequest containing username and number of generations to fill
     * @return the result of the fill request
     */
    public FillResult fill(FillRequest request) throws RequestException, DataAccessException, SQLException {
        if (request.getGenerations() < 0) {
            throw new RequestException("Error: Invalid number of generations");
        }
        Database db = new Database();
        try {
            Connection conn = db.openWithForeignKey(dbPath);
            PersonDAO personDAO = new PersonDAO(conn);


            // Delete all persons related to user
            // Delete events on cascade
            personDAO.deletePersonsByUsername(request.getUsername());
            db.close(true);
            conn = db.open(dbPath);
            personDAO.setConnection(conn);
            EventDAO eventDAO = new EventDAO(conn);

            // Create person object for user
            UserDAO userDAO = new UserDAO(db.getConnection());
            User user = userDAO.getUserByUsername(request.getUsername());
            Person userPerson = new Person(user.getPersonID(), user.getUsername(), user.getFirstName(),
                    user.getLastName(), user.getGender(), null, null, null);

            // Get data for generating ancestry
            parseJSONData();
            // Create first birth event
            Location birthLocation = locations[rng.nextInt(locations.length)];
            Event birth = new Event(UUID.randomUUID().toString(), user.getUsername(), userPerson.getPersonID(),
                    birthLocation.getLatitude(), birthLocation.getLongitude(), birthLocation.getCountry(),
                    birthLocation.getCity(), "birth", 2000);
            eventDAO.insert(birth);

            // Recursively add parents with events
            addParents(userPerson, request.getGenerations(), birth, personDAO, eventDAO);
            db.close(true);
            return new FillResult("Successfully added " + numPersonsAdded + " persons and " +
                            numEventsAdded + " events to the database", true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            return new FillResult("Error: " + e.getMessage(), false);
        }
        catch (IOException | DecodeException e) {
            e.printStackTrace();
            return new FillResult("Error: Unable to generate random data", false);
        }
        finally {
            if (!db.isClosed()) {
                db.close(false);
            }
        }
    }

    /**
     * Parse JSON data for random generation
     * @throws IOException if file does not exist
     * @throws DecodeException on failure to decode and parse json
     */
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
     * @param child Person object of the child of the parents
     * @param generations Number of generations left
     * @param childBirth Birth event of the child
     * @param personDAO PersonDAO connection for adding persons
     * @param eventDAO EventDAO connection for adding events
     */
    private void addParents(Person child, int generations, Event childBirth,
                            PersonDAO personDAO, EventDAO eventDAO) throws DataAccessException {
        if (generations == 0) {
            personDAO.insert(child);
            numPersonsAdded++;
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

        // Create events
        Location mBirthLocation = locations[rng.nextInt(locations.length)];
        Event mBirth = new Event(UUID.randomUUID().toString(), child.getAssociatedUsername(), mother.getPersonID(),
                mBirthLocation.getLatitude(), mBirthLocation.getLongitude(), mBirthLocation.getCountry(),
                mBirthLocation.getCity(), "birth",
                rng.nextInt(childBirth.getYear() - 45, childBirth.getYear() - 18));
        Location fBirthLocation = locations[rng.nextInt(locations.length)];
        Event fBirth = new Event(UUID.randomUUID().toString(), child.getAssociatedUsername(), father.getPersonID(),
                fBirthLocation.getLatitude(), fBirthLocation.getLongitude(), fBirthLocation.getCountry(),
                fBirthLocation.getCity(), "birth",
                rng.nextInt(childBirth.getYear() - 50, childBirth.getYear() - 18));
        Location marriageLocation = locations[rng.nextInt(locations.length)];
        int marriageBound = Math.max(fBirth.getYear(), mBirth.getYear()) + 18;
        int marriageYear = rng.nextInt(marriageBound, childBirth.getYear());
        Event mMarriage = new Event(UUID.randomUUID().toString(), child.getAssociatedUsername(), mother.getPersonID(),
                marriageLocation.getLatitude(), marriageLocation.getLongitude(), marriageLocation.getCountry(),
                marriageLocation.getCity(), "marriage", marriageYear);
        Event fMarriage = new Event(UUID.randomUUID().toString(), child.getAssociatedUsername(), father.getPersonID(),
                marriageLocation.getLatitude(), marriageLocation.getLongitude(), marriageLocation.getCountry(),
                marriageLocation.getCity(), "marriage", marriageYear);

        // Assume parents and grandparents are still alive
        if (generations < 3) {
            Location mBaptismLocation = locations[rng.nextInt(locations.length)];
            Location fBaptismLocation = locations[rng.nextInt(locations.length)];
            Event mBaptism = new Event(UUID.randomUUID().toString(), child.getAssociatedUsername(), mother.getPersonID(),
                    fBirthLocation.getLatitude(), fBirthLocation.getLongitude(), fBirthLocation.getCountry(),
                    fBirthLocation.getCity(), "baptism",
                    rng.nextInt(mBirth.getYear() + 8, childBirth.getYear()));
            Event fBaptism = new Event(UUID.randomUUID().toString(), child.getAssociatedUsername(), father.getPersonID(),
                    fBirthLocation.getLatitude(), fBirthLocation.getLongitude(), fBirthLocation.getCountry(),
                    fBirthLocation.getCity(), "baptism",
                    rng.nextInt(fBirth.getYear() + 8, childBirth.getYear()));
            eventDAO.insert(mBaptism);
            eventDAO.insert(fBaptism);
            numEventsAdded += 2;
        }
        else {
            Location deathLocation = locations[rng.nextInt(locations.length)];
            Event mDeath = new Event(UUID.randomUUID().toString(), child.getAssociatedUsername(), mother.getPersonID(),
                    deathLocation.getLatitude(), deathLocation.getLongitude(), deathLocation.getCountry(),
                    fBirthLocation.getCity(), "death",
                    rng.nextInt(childBirth.getYear(), mBirth.getYear() + 100));
            Event fDeath = new Event(UUID.randomUUID().toString(), child.getAssociatedUsername(), father.getPersonID(),
                    deathLocation.getLatitude(), deathLocation.getLongitude(), deathLocation.getCountry(),
                    deathLocation.getCity(), "death",
                    rng.nextInt(childBirth.getYear(), fBirth.getYear() + 100));
            eventDAO.insert(mDeath);
            eventDAO.insert(fDeath);
            numEventsAdded += 2;
        }


        personDAO.insert(child);
        numPersonsAdded++;
        eventDAO.insert(mBirth);
        eventDAO.insert(fBirth);
        eventDAO.insert(mMarriage);
        eventDAO.insert(fMarriage);
        numEventsAdded += 4;

        int fatherGenerations = --generations;
        addParents(mother, generations, mBirth, personDAO, eventDAO);
        addParents(father, fatherGenerations, fBirth, personDAO, eventDAO);
    }
}
