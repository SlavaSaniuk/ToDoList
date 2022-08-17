package by.beltelecom.todolist.integration.serviecs.security;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.security.SignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@DataJpaTest
public class SignServiceTestsCase {

    @Autowired
    private SignService signService;

    @Test
    void registerAccount_newAccount_shouldReturnRegisteredAccount() {
        Account toAccount = new Account();
        toAccount.setEmail("anyEmail");
        toAccount.setPassword("anyPassword");
        User toUser = new User();
        toUser.setName("anyName");

        Account created = this.signService.registerAccount(toAccount, toUser);
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getEmail());
        Assertions.assertNotNull(created.getPassword());
        Assertions.assertNotNull(created.getUserAccount());
        Assertions.assertNotNull(created.getUserAccount().getName());

        Assertions.assertNotEquals(0L, created.getId());
        Assertions.assertNotEquals(0L, created.getUserAccount().getId());
    }
}
