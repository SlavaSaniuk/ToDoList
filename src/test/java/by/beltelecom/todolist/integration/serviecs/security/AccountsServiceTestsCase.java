package by.beltelecom.todolist.integration.serviecs.security;

import by.beltelecom.todolist.configuration.bean.TestUser;
import by.beltelecom.todolist.configuration.bean.TestsUsersService;
import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.security.authentication.SignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestsUsersService.class)
public class AccountsServiceTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsServiceTestsCase.class);

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private TestsUsersService testsUsersService;

    @Autowired
    private SignService signService;

    @Test
    void getAccountByEmail_accountNotFounded_shouldReturnEmptyOptional() {
        Assertions.assertFalse(this.accountsService.getAccountOptByEmail("anyEmail").isPresent());
    }

    @Test
    void getAccountByEmail_accountFounded_shouldReturnEOptionalOfAccount() {
        Account account = new Account();
        account.setEmail("anyEmail");
        account.setPassword("anyPassword");
        User user = new User();
        user.setName("anyName");
        signService.registerAccount(account, user);

        Assertions.assertNotNull(this.accountsService.getAccountByEmail("anyEmail"));
    }

    @Test
    void getAccountByEmail_accountExist_shouldReturnAccountWithUserEntity() {
        // Check test users service:
        Assertions.assertNotNull(this.testsUsersService);
        TestUser testUser = testsUsersService.registerUser();
        LOGGER.debug("Test user: " +testUser);

        // Get account by service:
        Account foundedAccount = this.accountsService.getAccountByEmail(testUser.getAccount().getEmail());
        Assertions.assertNotNull(foundedAccount);

        // Check if user already initialized:
        User user = foundedAccount.getUserOwner();
        Assertions.assertNotNull(user);
        Assertions.assertNotEquals(0L, user.getId());
        Assertions.assertNotNull(user.getName());
        LOGGER.debug("Founded user: " +user);

        // Delete created user:
        this.testsUsersService.deleteUser(testUser);
    }
}
