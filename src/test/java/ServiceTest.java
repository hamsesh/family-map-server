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
        db.open(TEST_DB_PATH);
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
        AuthToken token = new AuthToken(response.getAuthToken(), "jimbob-77");
        boolean authenticateSuccess = authTokenDAO.validate(token);

        db.close(false);

        Assertions.assertNotNull(foundUser);
        Assertions.assertTrue(authenticateSuccess);
    }

    @Test
    @DisplayName("Fill 5 generations")
    public void testFillFiveGenerations() throws DataAccessException {
        Connection conn = db.open(TEST_DB_PATH);
        UserDAO userDAO = new UserDAO(conn);
        userDAO.insert(new User("jiminy-cricket", "password", "jiminy@test.com",
                "Jiminy", "Cricket", "m", UUID.randomUUID().toString()));
        db.close(true);

        try {
            FillRequest fillRequest = new FillRequest("jiminy-cricket", 5);
            FillService fillService = new FillService(TEST_DB_PATH);
            FillResult fillResult = fillService.fill(fillRequest);
            conn = db.open(TEST_DB_PATH);
            PersonDAO personDAO = new PersonDAO(conn);
            EventDAO eventDAO = new EventDAO(conn);
            Person[] familyTree = personDAO.getAllPersonsByUsername("jiminy-cricket");
            Event[] familyEvents = eventDAO.getAllEventsByUsername("jiminy-cricket");

            Person firstGen = personDAO.getPersonByID(fillResult.getPersonID());
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
}
