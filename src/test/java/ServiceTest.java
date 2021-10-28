import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;
import request.*;
import result.*;
import service.*;

import java.io.File;
import java.sql.Connection;
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
        Assertions.assertNotNull(userDAO.getUserByUsername("jimbob-77"));

        PersonDAO personDAO = new PersonDAO(db.getConnection());
        Assertions.assertNotNull(personDAO.getPersonByID(response.getPersonID()));

        AuthTokenDAO authTokenDAO = new AuthTokenDAO(db.getConnection());
        Assertions.assertTrue(authTokenDAO.validate(new AuthToken(response.getAuthToken(), "jimbob-77")));

        db.close(false);
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
            PersonDAO personDAO = new PersonDAO(db.open(TEST_DB_PATH));
            Person[] familyTree = personDAO.getAllPersonsByUsername("jiminy-cricket");



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
        }
        catch (RequestException e) {
            db.close(false);
            e.printStackTrace();
        }
    }
}
