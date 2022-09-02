package by.beltelecom.todolist.configuration.bean;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenService;
import by.beltelecom.todolist.services.users.UsersService;
import by.beltelecom.todolist.utilities.logging.Checks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class TestsUsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestsUsersService.class);

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
    public TestUser testUser;

    public TestUser registerUser() {
        Account account = new Account();
        account.setEmail(ACCOUNT_EMAIL);
        account.setPassword(ACCOUNT_PASSWORD);

        User user = new User();
        user.setName(USER_NAME);

        account = this.signService.registerAccount(account, user);

        // Generate JWT:
        String jwt = this.jsonWebTokenService.generateToken(user.getName());

        return new TestUser(account.getUserOwner(), account, jwt);
    }

    public void deleteUser(TestUser aTestUser) {
        Objects.requireNonNull(aTestUser, Checks.argumentNotNull("aTestUser", TestUser.class));
        this.usersService.deleteUser(aTestUser.getUser());
    }


    @Getter
    @AllArgsConstructor
    public static class TestUser {

        private User user;
        private Account account;
        private String jwtToken;
    }
}
