package by.beltelecom.todolist.integration.serviecs.security;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.security.authentication.SignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountsServiceTestsCase {

    @Autowired
    private AccountsService accountsService;

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
}
