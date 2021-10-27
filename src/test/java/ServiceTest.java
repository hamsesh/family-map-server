import dao.*;
import model.AuthToken;
import org.junit.jupiter.api.*;
import request.*;
import result.*;
import service.*;

import java.io.File;

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
        RegisterResult response;
        RegisterRequest request = new RegisterRequest("jimbob-77", "password", "test@test.com",
                "Jim", "Bob", "m");
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
}
