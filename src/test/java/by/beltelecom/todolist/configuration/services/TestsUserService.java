package by.beltelecom.todolist.configuration.services;

import by.beltelecom.todolist.configuration.models.TestingUser;
import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenService;
import by.beltelecom.todolist.services.security.role.RoleService;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestsUserService {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(TestsUserService.class);
    // Spring beans:
    @Autowired
    private SignService signService;
    @Autowired
    private JsonWebTokenService jsonWebTokenService;
    @Autowired
    private RoleService roleService;

    public TestsUserService() {
        LOGGER.debug(SpringLogging.Creation.createBean(TestsUserService.class));
    }

    public TestingUser createUser(String aNamePrefix) {
        LOGGER.debug(String.format("Create TestingUser object with name prefix: %s", aNamePrefix));
        // Create testing user:
        TestingUser testingUser = new TestingUser.Builder()
                .setUserName("Test_Name_" +aNamePrefix)
                .setAccountEmail("test_email_" +aNamePrefix +"@mail.com")
                .build();

        LOGGER.debug(String.format("%s - CREATED;", testingUser));
        return testingUser;
    }

    public TestingUser registerUser(TestingUser aTestingUser) {
        LOGGER.debug(String.format("Register testing user: %s", aTestingUser));
        Account registered = this.signService.registerAccount(aTestingUser.getAccount(), aTestingUser.getUser());

        // Generate jwt:
        String jwt = this.jsonWebTokenService.generateToken(registered.getEmail());

        TestingUser testingUser =  new TestingUser(registered, registered.getUserOwner(), jwt);
        LOGGER.debug(String.format("%s - REGISTERED;", testingUser));

        return testingUser;
    }

    public TestingUser testingUser(String aNamePrefix) {
        // Create and register:
        return this.registerUser(this.createUser(aNamePrefix));
    }

    /**
     * Create {@link TestingUser} user with specified application role.
     * @param aUserRole - user role.
     * @param aNamePrefix - user name prefix.
     * @return - testing user.
     */
    public TestingUser userWithRole(UserRole aUserRole, String aNamePrefix) {
        // Check arguments:
        ArgumentChecker.nonNull(aUserRole, "aUserRole");
        ArgumentChecker.nonNull(aNamePrefix, "aNamePrefix");

        LOGGER.debug(String.format("Create testing user with name prefix[%s] of role[%s];", aNamePrefix, aUserRole));

        // Create testing user:
        TestingUser testingUser = this.testingUser(aNamePrefix);

        // Add user role:
        try {
            this.roleService.addRoleToUser(aUserRole, testingUser.getUser());
            return testingUser;
        } catch (NotFoundException e) {
            throw new RuntimeException(String.format("Cannot create testing user with name prefix[%s];", aNamePrefix));
        }
    }

    /**
     * Create {@link TestingUser} user with "ROLE_ROOT_ADMIN" admin role.
     * @param aNamePrefix - user name prefix.
     * @return - testing root administrator user.
     */
    public TestingUser testingRootAdmin(String aNamePrefix) {
        ArgumentChecker.nonNull(aNamePrefix, "aNamePrefix");

        LOGGER.debug(String.format("Create testing user with name prefix[%s] with [ROLE_ROOT_ADMIN] role;", aNamePrefix));
        return this.userWithRole(UserRole.ROLE_ROOT_ADMIN, aNamePrefix);
    }
}
