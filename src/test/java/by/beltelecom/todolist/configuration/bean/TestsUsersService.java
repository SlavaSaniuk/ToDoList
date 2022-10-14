package by.beltelecom.todolist.configuration.bean;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenService;
import by.beltelecom.todolist.services.users.UsersService;
import by.beltelecom.todolist.utilities.logging.Checks;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * {@link TestsUsersService} service bean used in integration tests to simplify process registration users and accounts.
 * Use {@link TestsUsersService#registerUser()} method to get {@link TestUser} object
 * and {@link TestsUsersService#deleteUser(TestUser)} method to delete test user.
 */
public class TestsUsersService {

    public static final String USER_NAME = "userName";
    public static final String ACCOUNT_EMAIL = "email@mail.com";
    public static final String ACCOUNT_PASSWORD = "!1AaBbCcDdEe";

    @Autowired
    private UsersService usersService;
    @Autowired
    private SignService signService;
    @Autowired
    private JsonWebTokenService jsonWebTokenService;

    @Getter
    private TestUser testUser;

    public TestUser registerUser() {
        Account account = new Account();
        account.setEmail(ACCOUNT_EMAIL);
        account.setPassword(ACCOUNT_PASSWORD);

        User user = new User();
        user.setName(USER_NAME);

        account = this.signService.registerAccount(account, user);

        // Generate JWT:
        String jwt = this.jsonWebTokenService.generateToken(account.getEmail());

        this.testUser = new TestUser(account.getUserOwner(), account, jwt);
        return this.testUser;
    }

    public void deleteUser(TestUser aTestUser) {
        Objects.requireNonNull(aTestUser, Checks.argumentNotNull("aTestUser", TestUser.class));
        this.usersService.deleteUser(aTestUser.getUser());
        this.testUser = null;
    }

}
