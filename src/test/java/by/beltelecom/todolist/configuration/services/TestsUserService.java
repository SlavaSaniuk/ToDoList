package by.beltelecom.todolist.configuration.services;

import by.beltelecom.todolist.configuration.models.TestingUser;
import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.security.authentication.SignService;
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

        TestingUser testingUser =  new TestingUser(registered, registered.getUserOwner());
        LOGGER.debug(String.format("%s - REGISTERED;", testingUser));

        return testingUser;
    }

    public TestingUser testingUser(String aNamePrefix) {
        // Create and register:
        return this.registerUser(this.createUser(aNamePrefix));
    }
}
