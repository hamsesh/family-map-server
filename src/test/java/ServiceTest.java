import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;
import request.*;
import result.*;
import service.*;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;

public class ServiceTest {
    private static final String TEST_DB_PATH = "sql" + File.separator + "test-db.db";
    private static Database db;

    // Create db file and open connection
    @BeforeAll
    public static void setUp() throws DataAccessException {
        db = new Database();
    }

    // Clear tables
    @AfterAll
    public static void cleanUp() throws DataAccessException {
        db.open(TEST_DB_PATH);
        db.clearTables();
        db.close(true);
    }

    @Test
    @DisplayName("Successful register test")
    public void testRegister() throws DataAccessException {
        RegisterRequest request = new RegisterRequest("jimbob-77", "password", "test@test.com",
                "Jim", "Bob", "m");
        RegisterResult response;
        RegisterService service = new RegisterService(TEST_DB_PATH);
        response = service.register(request);
        Assertions.assertTrue(response.isSuccess());

        UserDAO userDAO = new UserDAO(db.open(TEST_DB_PATH));
        User foundUser = userDAO.getUserByUsername("jimbob-77");

        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
        String authUsername = authTokenDAO.validate(response.getAuthToken());

        db.close(false);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(authUsername, "jimbob-77");
    }

    @Test
    @DisplayName("Fill two generations")
    public void testFillTwoGenerations() throws DataAccessException {
        Connection conn = db.open(TEST_DB_PATH);
        UserDAO userDAO = new UserDAO(conn);
        userDAO.insert(new User("wallabee-cricket", "password", "wallabee@test.com",
                "Wallabee", "Cricket", "m", UUID.randomUUID().toString()));
        db.close(true);

        try {
            FillRequest fillRequest = new FillRequest("wallabee-cricket", 2);
            FillService fillService = new FillService(TEST_DB_PATH);
            fillService.fill(fillRequest);
            conn = db.open(TEST_DB_PATH);
            PersonDAO personDAO = new PersonDAO(conn);
            EventDAO eventDAO = new EventDAO(conn);
            Person[] familyTree = personDAO.getAllPersonsByUsername("wallabee-cricket");
            Event[] familyEvents = eventDAO.getAllEventsByUsername("wallabee-cricket");

            Person firstGen = personDAO.getPersonByID(familyTree[0].getPersonID());
            Person secondGen = personDAO.getPersonByID(firstGen.getMotherID());
            Person thirdGen = personDAO.getPersonByID(secondGen.getFatherID());
            db.close(false);

            Assertions.assertEquals(7, familyTree.length);
            Assertions.assertNotNull(firstGen);
            Assertions.assertNotNull(secondGen);

            // Test valid events
            Assertions.assertEquals(19, familyEvents.length);
            HashMap<String, HashMap<String, Event>> eventMap = new HashMap<>();
            for (Event event : familyEvents) {
                if (!eventMap.containsKey(event.getPersonID())) {
                    HashMap<String, Event> personEvents = new HashMap<>();
                    personEvents.put(event.getEventType(), event);
                    eventMap.put(event.getPersonID(), personEvents);
                }
                eventMap.get(event.getPersonID()).put(event.getEventType(), event);
            }

            for (Person person : familyTree) {
                HashMap<String, Event> personEvents = eventMap.get(person.getPersonID());
                Event birth = personEvents.get("birth");
                if (personEvents.containsKey("death")) {
                    Event death = personEvents.get("death");
                    Assertions.assertTrue(death.getYear() - birth.getYear() > 18);
                    Assertions.assertTrue(death.getYear() - birth.getYear() < 110);
                    if (personEvents.containsKey("marriage")) {
                        Event marriage = personEvents.get("marriage");
                        Assertions.assertTrue(death.getYear() - marriage.getYear() > 0);
                    }
                    if (personEvents.containsKey("baptism")) {
                        Event baptism = personEvents.get("baptism");
                        Assertions.assertTrue(death.getYear() - baptism.getYear() > 18);
                    }
                }
                if (personEvents.containsKey("marriage")) {
                    Event marriage = personEvents.get("marriage");
                    Assertions.assertTrue(marriage.getYear() - birth.getYear() >= 18);
                }
                Person mother = personDAO.getPersonByID(person.getMotherID());
                if (mother != null) {
                    Event motherBirth = eventMap.get(mother.getPersonID()).get("birth");
                    Assertions.assertTrue(motherBirth.getYear() - birth.getYear() > 18);
                }
            }
        }
        catch (Exception e) {
            db.close(false);
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Fill five generations")
    public void testFillFiveGenerations() throws DataAccessException {
        Connection conn = db.open(TEST_DB_PATH);
        UserDAO userDAO = new UserDAO(conn);
        userDAO.insert(new User("jiminy-cricket", "password", "jiminy@test.com",
                "Jiminy", "Cricket", "m", UUID.randomUUID().toString()));
        db.close(true);

        try {
            FillRequest fillRequest = new FillRequest("jiminy-cricket", 5);
            FillService fillService = new FillService(TEST_DB_PATH);
            fillService.fill(fillRequest);
            conn = db.open(TEST_DB_PATH);
            PersonDAO personDAO = new PersonDAO(conn);
            EventDAO eventDAO = new EventDAO(conn);
            Person[] familyTree = personDAO.getAllPersonsByUsername("jiminy-cricket");
            Event[] familyEvents = eventDAO.getAllEventsByUsername("jiminy-cricket");

            Person firstGen = personDAO.getPersonByID(familyTree[0].getPersonID());
            Person secondGen = personDAO.getPersonByID(firstGen.getMotherID());
            Person thirdGen = personDAO.getPersonByID(secondGen.getFatherID());
            Person fourthGen = personDAO.getPersonByID(thirdGen.getMotherID());
            Person fifthGen = personDAO.getPersonByID(fourthGen.getFatherID());
            db.close(false);

            Assertions.assertEquals(63, familyTree.length);
            Assertions.assertNotNull(firstGen);
            Assertions.assertNotNull(secondGen);
            Assertions.assertNotNull(thirdGen);
            Assertions.assertNotNull(fourthGen);
            Assertions.assertNotNull(fifthGen);

            // Test valid events
            Assertions.assertEquals(187, familyEvents.length);
            HashMap<String, HashMap<String, Event>> eventMap = new HashMap<>();
            for (Event event : familyEvents) {
                if (!eventMap.containsKey(event.getPersonID())) {
                    HashMap<String, Event> personEvents = new HashMap<>();
                    personEvents.put(event.getEventType(), event);
                    eventMap.put(event.getPersonID(), personEvents);
                }
                eventMap.get(event.getPersonID()).put(event.getEventType(), event);
            }

            for (Person person : familyTree) {
                HashMap<String, Event> personEvents = eventMap.get(person.getPersonID());
                Event birth = personEvents.get("birth");
                if (personEvents.containsKey("death")) {
                    Event death = personEvents.get("death");
                    Assertions.assertTrue(death.getYear() - birth.getYear() > 18);
                    Assertions.assertTrue(death.getYear() - birth.getYear() < 110);
                    if (personEvents.containsKey("marriage")) {
                        Event marriage = personEvents.get("marriage");
                        Assertions.assertTrue(death.getYear() - marriage.getYear() > 0);
                    }
                    if (personEvents.containsKey("baptism")) {
                        Event baptism = personEvents.get("baptism");
                        Assertions.assertTrue(death.getYear() - baptism.getYear() > 18);
                    }
                }
                if (personEvents.containsKey("marriage")) {
                    Event marriage = personEvents.get("marriage");
                    Assertions.assertTrue(marriage.getYear() - birth.getYear() >= 18);
                }
                Person mother = personDAO.getPersonByID(person.getMotherID());
                if (mother != null) {
                    Event motherBirth = eventMap.get(mother.getPersonID()).get("birth");
                    Assertions.assertTrue(motherBirth.getYear() - birth.getYear() > 18);
                }
            }
        }
        catch (RequestException e) {
            db.close(false);
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Fill invalid generations")
    public void testFillInvalidGenerations() throws DataAccessException {
        Connection conn = db.open(TEST_DB_PATH);
        UserDAO userDAO = new UserDAO(conn);
        userDAO.insert(new User("jelly-von-winkle", "password", "jelly@test.com",
                "Jelly", "VonWinkle", "m", UUID.randomUUID().toString()));
        db.close(true);

        FillRequest request = new FillRequest("jelly-von-winkle", -1);
        FillService service = new FillService(TEST_DB_PATH);
        Assertions.assertThrows(RequestException.class, () -> {
            service.fill(request);
        });
    }

    @Test
    @DisplayName("Clear database")
    public void testClearDatabase() throws DataAccessException {
        User foundUser;
        Person foundPerson;
        Event foundEvent;
        String authUsername;

        db.open(TEST_DB_PATH);
        DAOTest.fill(db);
        ClearService service = new ClearService(TEST_DB_PATH);
        ClearResult result = service.clear();

        Assertions.assertTrue(result.isSuccess());
        UserDAO userDAO = new UserDAO((db.open(TEST_DB_PATH)));
        try {
            foundUser = userDAO.getUserByUsername("jim_halpert");
        }
        catch (DataAccessException e) {
            foundUser = null;
        }

        PersonDAO personDAO = new PersonDAO(db.getConnection());
        try {
            foundPerson = personDAO.getPersonByID("ar5j92");
        }
        catch (DataAccessException e) {
            foundPerson = null;
        }

        EventDAO eventDAO = new EventDAO(db.getConnection());
        try {
            foundEvent = eventDAO.getEventByID("jm1q90");
        }
        catch (DataAccessException e) {
            foundEvent = null;
        }

        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
        try {
            authUsername = authTokenDAO.validate("cme342018");
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            authUsername = null;
        }
        db.close(false);

        Assertions.assertNull(foundUser);
        Assertions.assertNull(foundPerson);
        Assertions.assertNull(foundEvent);
        Assertions.assertNull(authUsername);
    }

    @Test
    @DisplayName("Login user")
    public void testLoginUser() throws DataAccessException {
        Connection conn = db.open(TEST_DB_PATH);
        UserDAO userDAO = new UserDAO(conn);
        userDAO.insert(new User("bobby_boy", "secret", "bobby@test.com",
                "Bobby", "Boy", "m", UUID.randomUUID().toString()));
        db.close(true);

        LoginRequest request = new LoginRequest("bobby_boy", "secret");
        LoginService service = new LoginService(TEST_DB_PATH);
        LoginResult result = service.login(request);

        Assertions.assertTrue(result.isSuccess());
        Assertions.assertNotNull(result.getPersonID());
        Assertions.assertNotNull(result.getUsername());
        Assertions.assertNotNull(result.getAuthToken());
        Assertions.assertNull(result.getMessage());
    }

    @Test
    @DisplayName("Login bad password")
    public void testLoginBadPassword() throws DataAccessException {
        Connection conn = db.open(TEST_DB_PATH);
        UserDAO userDAO = new UserDAO(conn);
        userDAO.insert(new User("billy_bob", "secret", "billy@test.com",
                "Billy", "Bob", "m", UUID.randomUUID().toString()));
        db.close(true);

        LoginRequest request = new LoginRequest("bobby_boy", "incorrect");
        LoginService service = new LoginService(TEST_DB_PATH);
        LoginResult result = service.login(request);

        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNotNull(result.getMessage());
    }

    @Test
    @DisplayName("Get persons with valid token")
    public void testGetPersonsValidAuth() throws DataAccessException {
        db.open(TEST_DB_PATH);
        DAOTest.fill(db);

        PersonService personService = new PersonService(TEST_DB_PATH);
        PersonResult personResult = personService.person("qor492048");
        Person correctPerson = new Person("mn2c89", "michael_scott", "Abraham",
                "Lincoln", "m", "ar5j92", null, null);

        db.open(TEST_DB_PATH);
        db.clearTables();
        db.close(true);

        Assertions.assertNotNull(personResult);
        Assertions.assertTrue(personResult.isSuccess());
        Assertions.assertNull(personResult.getMessage());
        Assertions.assertEquals(personResult.getData().length, 1);
        Assertions.assertEquals(personResult.getData()[0], correctPerson);
    }

    @Test
    @DisplayName("Get persons with invalid token")
    public void testGetPersonsInvalidAuth() throws DataAccessException {
        db.open(TEST_DB_PATH);
        DAOTest.fill(db);

        PersonService personService = new PersonService(TEST_DB_PATH);
        PersonResult personResult = personService.person("INVALID");

        db.open(TEST_DB_PATH);
        db.clearTables();
        db.close(true);

        Assertions.assertNotNull(personResult);
        Assertions.assertFalse(personResult.isSuccess());
        Assertions.assertNotNull(personResult.getMessage());
        Assertions.assertNull(personResult.getData());
    }

    @Test
    @DisplayName("Get person by ID")
    public void testGetPersonByID() throws DataAccessException {
        db.open(TEST_DB_PATH);
        DAOTest.fill(db);

        PersonIDService personIDService = new PersonIDService(TEST_DB_PATH);
        PersonIDResult personIDResult = personIDService.personID("cme342018", "ar5j92");
        Person correctPerson = new Person("ar5j92", "jim_halpert", "Old",
                "McDonald", "m", null, null, null);

        db.open(TEST_DB_PATH);
        db.clearTables();
        db.close(true);

        Assertions.assertNotNull(personIDResult);
        Assertions.assertTrue(personIDResult.isSuccess());
        Assertions.assertNull(personIDResult.getMessage());
        Assertions.assertNotNull(personIDResult.getPersonID());

        Person foundPerson = new Person(personIDResult.getPersonID(), personIDResult.getAssociatedUsername(),
                personIDResult.getFirstName(), personIDResult.getLastName(), personIDResult.getGender(),
                personIDResult.getFatherID(), personIDResult.getMotherID(), personIDResult.getSpouseID());

        Assertions.assertEquals(foundPerson, correctPerson);
    }

    @Test
    @DisplayName("Get person by invalid ID")
    public void testGetPersonByInvalidID() throws DataAccessException {
        db.open(TEST_DB_PATH);
        DAOTest.fill(db);

        PersonIDService personIDService = new PersonIDService(TEST_DB_PATH);
        PersonIDResult personIDResult = personIDService.personID("qor492048", "000000");

        db.open(TEST_DB_PATH);
        db.clearTables();
        db.close(true);

        Assertions.assertNotNull(personIDResult);
        Assertions.assertFalse(personIDResult.isSuccess());
        Assertions.assertNotNull(personIDResult.getMessage());
        Assertions.assertNull(personIDResult.getPersonID());
    }

    @Test
    @DisplayName("Get another user's person")
    public void testGetOtherPerson() throws DataAccessException {
        db.open(TEST_DB_PATH);
        DAOTest.fill(db);

        PersonIDService personIDService = new PersonIDService(TEST_DB_PATH);
        PersonIDResult personIDResult = personIDService.personID("cme342018", "mn2c89");

        db.open(TEST_DB_PATH);
        db.clearTables();
        db.close(true);

        Assertions.assertNotNull(personIDResult);
        Assertions.assertFalse(personIDResult.isSuccess());
        Assertions.assertNotNull(personIDResult.getMessage());
        Assertions.assertNull(personIDResult.getPersonID());
    }
}
