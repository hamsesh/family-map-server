import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;

import java.io.File;

import dao.*;

public class DAOTest {

    private static final String TEST_DB_PATH = "sql" + File.separator + "test-db.db";
    private static Database db;

    // Create db file and open connection
    @BeforeAll
    public static void setUp() throws DataAccessException {
        db = new Database();
        db.open(TEST_DB_PATH);
    }

    @BeforeEach
    public void openConnection() throws DataAccessException {
        db.open(TEST_DB_PATH);
    }

    @AfterEach
    public void closeConnection() throws DataAccessException {
        db.close(false);
    }

    @AfterAll
    static void clearTables() throws DataAccessException {
        db.open(TEST_DB_PATH);
        db.clearTables();
        db.close(true);
    }

    @Test
    @DisplayName("Insert valid user")
    public void testInsertValidUser() {
        boolean success = false;
        User newUser = new User("Scrooge_Mcduck", "password", "scrooge@yahoo.com",
                "Scrooge", "McDuck", "m", "000000");
        try {
            UserDAO dao = new UserDAO(db.getConnection());
            dao.insert(newUser);
            success = true;
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(success);
    }

    @Test
    @DisplayName("Insert user with same username")
    public void testInsertDuplicateUser() {
        String exceptionMessage = null;
        User newUser = new User("scrooge_mcduck", "password", "scrooge@yahoo.com",
                "Scrooge", "McDuck", "m", "000000");
        User dupeUser = new User("scrooge_mcduck", "password", "dewey@yahoo.com",
                "Dewey", "McDuck", "m", "000001");
        try {
            UserDAO userDAO = new UserDAO(db.getConnection());
            userDAO.insert(newUser);
            userDAO.insert(dupeUser);
        }
        catch (DataAccessException e) {
            exceptionMessage = e.getMessage();
        }
        Assertions.assertEquals("[SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed " +
                "(UNIQUE constraint failed: users.username)", exceptionMessage);
    }

    @Test
    @DisplayName("Retrieve user by username")
    public void testGetUserByUsername() throws DataAccessException {
        User newUser = new User("donald_duck", "password", "donald@yahoo.com", "Donald",
                "Duck", "m", "000000");
        User newUser2 = new User("dewey_duck", "passw0rd", "dewey@yahoo.com", "Dewey",
                "Duck", "m", "000001");
        UserDAO dao = new UserDAO(db.getConnection());
        dao.insert(newUser);
        dao.insert(newUser2);
        db.close(true);

        dao.setConnection(db.open(TEST_DB_PATH));
        User foundUser = dao.getUserByUsername("donald_duck");

        Assertions.assertEquals(newUser, foundUser);

        foundUser = dao.getUserByUsername("dewey_duck");

        Assertions.assertEquals(newUser2, foundUser);

        db.clearTables();
        db.close(true);

        db.open(TEST_DB_PATH);
    }

    @Test
    @DisplayName("Attempt to retrieve missing user")
    public void testGetMissingUser() throws DataAccessException {
        User newUser = new User("scrooge_mcduck", "password", "scrooge@yahoo.com",
                "Scrooge", "McDuck", "m", "000000");
        User foundUser;

        UserDAO dao = new UserDAO(db.getConnection());
        dao.insert(newUser);
        db.close(true);

        dao.setConnection(db.open(TEST_DB_PATH));
        try {
            foundUser = dao.getUserByUsername("dewey_duck");
        }
        catch (DataAccessException e) {
            foundUser = null;
        }

        Assertions.assertNull(foundUser);
    }

    @Test
    @DisplayName("Get person by ID")
    public void testGetPersonByID() throws DataAccessException {
        Person newPerson = new Person("ar5j92", "michael_scott", "Old",
                "McDonald", "m", null, null, null);
        Person newPerson2 = new Person("mn2c89", "michael_scott", "Abraham",
                "Lincoln", "m", "ar5j92", null, null);
        Person foundPerson;

        PersonDAO personDAO = new PersonDAO(db.getConnection());
        personDAO.insert(newPerson);
        personDAO.insert(newPerson2);
        db.close(true);

        personDAO.setConnection(db.open(TEST_DB_PATH));
        try {
            foundPerson = personDAO.getPersonByID("ar5j92");
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            foundPerson = null;
        }

        Assertions.assertNotNull(foundPerson);
        Assertions.assertEquals("michael_scott", foundPerson.getAssociatedUsername());

        personDAO.deletePersons();
        db.close(true);
        db.open(TEST_DB_PATH);
    }

    @Test
    @DisplayName("Get all persons associated with username")
    public void testGetAllPersonsByUsername() throws DataAccessException {
        Person newPerson = new Person("ar5j92", "michael_scott", "Old",
                "McDonald", "m", null, null, null);
        Person newPerson2 = new Person("mn2c89", "michael_scott", "Abraham",
                "Lincoln", "m", "ar5j92", null, null);

        PersonDAO personDAO = new PersonDAO(db.getConnection());
        personDAO.insert(newPerson);
        personDAO.insert(newPerson2);
        db.close(true);

        personDAO.setConnection(db.open(TEST_DB_PATH));
        Person[] foundPersons = personDAO.getAllPersonsByUsername("michael_scott");

        Assertions.assertEquals(newPerson, foundPersons[0]);
        Assertions.assertEquals(newPerson2, foundPersons[1]);
        Assertions.assertNull(foundPersons[0].getFatherID());

        db.clearTables();
        db.close(true);
        db.open(TEST_DB_PATH);
    }

    @Test
    @DisplayName("Get all persons from invalid username")
    public void testGetPersonsInvalidUsername() throws DataAccessException {
        Person newPerson = new Person("ar5j92", "michael_scott", "Old",
                "McDonald", "m", null, null, null);

        PersonDAO personDAO = new PersonDAO(db.getConnection());
        personDAO.insert(newPerson);
        db.close(true);

        personDAO.setConnection(db.open(TEST_DB_PATH));
        Person[] foundPersons = personDAO.getAllPersonsByUsername("invalid_username");

        Assertions.assertNull(foundPersons);

        db.clearTables();
        db.close(true);
        db.open(TEST_DB_PATH);
    }

    @Test
    @DisplayName("Delete persons by username")
    public void testDeletePersonsByUsername() throws DataAccessException {
        fill(db);
        PersonDAO personDAO = new PersonDAO(db.openWithForeignKey(TEST_DB_PATH));
        personDAO.deletePersonsByUsername("jim_halpert");
        db.close(true);

        personDAO.setConnection(db.open(TEST_DB_PATH));
        Person foundPerson = personDAO.getPersonByID("ar5j92");
        EventDAO eventDAO = new EventDAO(db.getConnection());
        Event foundEvent = eventDAO.getEventByID("wr8m89");

        Assertions.assertNull(foundPerson);
        Assertions.assertNull(foundEvent);

        db.clearTables();
        db.close(true);
        db.open(TEST_DB_PATH);
    }

    @Test
    @DisplayName("Insert valid person")
    public void testInsertValidPerson() {
        boolean success = false;
        Person newPerson = new Person("ar5j78", "joe_millington", "Joe",
                "Millington", "m", null, null, null);
        try {
            PersonDAO personDAO = new PersonDAO(db.getConnection());
            personDAO.insert(newPerson);
            success = true;
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(success);
    }

    @Test
    @DisplayName("Insert person with null ID")
    public void testInsertPersonNoID() {
        String exceptionMessage = null;
        Person newPerson = new Person(null, "jim_halpert", "Old",
                "McDonald", "m", null, null, null);
        try {
            PersonDAO personDAO = new PersonDAO(db.getConnection());
            personDAO.insert(newPerson);
        }
        catch (DataAccessException e) {
            exceptionMessage = e.getMessage();
        }

        Assertions.assertEquals("[SQLITE_CONSTRAINT_NOTNULL]  A NOT NULL constraint failed (NOT NULL " +
                "constraint failed: persons.person_id)", exceptionMessage);
    }

    @Test
    @DisplayName("Clear users")
    public void testClearUsers() throws DataAccessException {
        User newUser = new User("jim_halpert", "password", "jim@yahoo.com", "Jim",
                "Halpert", "m", "ae4f59");
        User newUser2 = new User("michael_scott", "pazzword", "michael@yahoo.com",
                "Michael", "Scott", "m", "rf3c93");
        User foundUser;
        User foundUser2;

        UserDAO userDAO = new UserDAO(db.getConnection());
        userDAO.insert(newUser);
        userDAO.insert(newUser2);
        db.close(true);

        userDAO.setConnection(db.open(TEST_DB_PATH));
        userDAO.deleteUsers();
        db.close(true);

        userDAO.setConnection(db.open(TEST_DB_PATH));
        try {
            foundUser = userDAO.getUserByUsername("jim_halpert");
        }
        catch (DataAccessException e) {
            foundUser = null;
        }
        Assertions.assertNull(foundUser);

        try {
            foundUser2 = userDAO.getUserByUsername("michael_scott");
        }
        catch (DataAccessException e) {
            foundUser2 = null;
        }
        Assertions.assertNull(foundUser2);
    }

    @Test
    @DisplayName("Clear persons")
    public void testClearPersons() throws DataAccessException {
        Person newPerson = new Person("mx4p20", "leonard_peep", "Leonard",
                "Peep", "m", null, null, null);
        Person newPerson2 = new Person("vx6n05", "cloud_strife", "Cloud",
                "Strife", "m", "mx4p20", null, null);
        Person foundPerson;
        Person foundPerson2;

        PersonDAO personDAO = new PersonDAO(db.getConnection());
        personDAO.insert(newPerson);
        personDAO.insert(newPerson2);
        db.close(true);

        personDAO.setConnection(db.open(TEST_DB_PATH));
        personDAO.deletePersons();
        db.close(true);

        personDAO.setConnection(db.open(TEST_DB_PATH));
        try {
            foundPerson = personDAO.getPersonByID("mx4p20");
        }
        catch (DataAccessException e) {
            foundPerson = null;
        }
        Assertions.assertNull(foundPerson);

        try {
            foundPerson2 = personDAO.getPersonByID("vx6n05");
        }
        catch (DataAccessException e) {
            foundPerson2 = null;
        }
        Assertions.assertNull(foundPerson2);
    }

    @Test
    @DisplayName("Insert event")
    public void testInsertEvent() {
        boolean success = false;
        Event newEvent = new Event("000000", "stephen_colbert", "000000", 2.4f, 2.4f,
                "USA", "Provo", "birth", 1970);

        try {
            EventDAO eventDAO = new EventDAO(db.getConnection());
            eventDAO.insert(newEvent);
            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(success);
    }

    @Test
    @DisplayName("Get all events by username")
    public void testGetEventsByUsername() throws DataAccessException {
        Event newEvent = new Event("ar5j92", "michael_scott", "000000", 3f,
                100f, "USA", "Provo", "death", 1999);
        Event newEvent2 = new Event("mn2c89", "michael_scott", "000001", 5f,
                4f, "Mexico", "Mexico City", "birth", 1950);

        EventDAO eventDAO = new EventDAO(db.getConnection());
        eventDAO.insert(newEvent);
        eventDAO.insert(newEvent2);
        db.close(true);

        eventDAO.setConnection(db.open(TEST_DB_PATH));
        Event[] foundEvents = eventDAO.getAllEventsByUsername("michael_scott");

        Assertions.assertEquals(newEvent, foundEvents[0]);
        Assertions.assertEquals(newEvent2, foundEvents[1]);

        db.clearTables();
        db.close(true);
        db.open(TEST_DB_PATH);
    }

    @Test
    @DisplayName("Get all events from invalid username")
    public void testGetEventsInvalidUsername() throws DataAccessException {
        Event newEvent = new Event("429217", "michael_scott", "43823f",
                34f, 35f, "USA", "Salt Lake City", "birth", 1950);

        EventDAO eventDAO = new EventDAO(db.getConnection());
        eventDAO.insert(newEvent);
        db.close(true);

        eventDAO.setConnection(db.open(TEST_DB_PATH));
        Event[] foundEvents = eventDAO.getAllEventsByUsername("invalid_username");

        Assertions.assertNull(foundEvents);

        db.clearTables();
        db.close(true);
        db.open(TEST_DB_PATH);
    }

    @Test
    @DisplayName("Insert AuthToken")
    public void testInsertAuthToken() {
        boolean success = false;
        AuthToken token = new AuthToken("000000", "jimboy_banana");

        try {
            AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
            authTokenDAO.insert(token);
            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(success);
    }

    @Test
    @DisplayName("Clear tables")
    public void testClearTables() throws DataAccessException {
        User foundUser;
        Person foundPerson;
        Event foundEvent;
        String authTokenUsername;

        fill(db);

        db.open(TEST_DB_PATH);
        db.clearTables();
        db.close(true);

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
            authTokenUsername = authTokenDAO.validate("cme342018");
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            authTokenUsername = null;
        }

        Assertions.assertNull(foundUser);
        Assertions.assertNull(foundPerson);
        Assertions.assertNull(foundEvent);
        Assertions.assertNull(authTokenUsername);
    }

    // Fill tables with fake data
    public static void fill(Database db) throws DataAccessException {
        User newUser = new User("jim_halpert", "password", "jim@yahoo.com", "Jim",
                "Halpert", "m", "ae4f59");
        User newUser2 = new User("michael_scott", "pazzword", "michael@yahoo.com",
                "Michael", "Scott", "m", "rf3c93");
        Person newPerson = new Person("ar5j92", "jim_halpert", "Old",
                "McDonald", "m", null, null, null);
        Person newPerson2 = new Person("mn2c89", "michael_scott", "Abraham",
                "Lincoln", "m", "ar5j92", null, null);
        Event newEvent = new Event("jm1q90", "michael_scott", "mn2c89", 38.89037f,
                -77.03196f, "USA", "Washington D.C.", "death", 1865);
        Event newEvent2 = new Event("wr8m89", "jim_halpert", "ar5j92", 34.115784f,
                -117.302399f, "USA", "San Bernadino", "birth", 1940);
        AuthToken newToken = new AuthToken("cme342018", "jim_halpert");
        AuthToken newToken2 = new AuthToken("qor492048", "michael_scott");

        UserDAO userDAO = new UserDAO(db.getConnection());
        PersonDAO personDAO = new PersonDAO(db.getConnection());
        EventDAO eventDAO = new EventDAO(db.getConnection());
        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());

        userDAO.insert(newUser);
        userDAO.insert(newUser2);

        personDAO.insert(newPerson);
        personDAO.insert(newPerson2);

        eventDAO.insert(newEvent);
        eventDAO.insert(newEvent2);

        authTokenDAO.insert(newToken);
        authTokenDAO.insert(newToken2);

        db.close(true);
    }
}
