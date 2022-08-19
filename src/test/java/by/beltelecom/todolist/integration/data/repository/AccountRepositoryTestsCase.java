package by.beltelecom.todolist.integration.data.repository;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.AccountsRepository;
import by.beltelecom.todolist.data.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AccountRepositoryTestsCase {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void findByEmail_emailNotFound_shouldReturnEmptyOptional() {
        Optional<Account> accountOpt = this.accountsRepository.findByEmail("anyEmail");
        Assertions.assertFalse(accountOpt.isPresent());
    }

    @Test
    void findByEmail_emailIsFound_shouldReturnAccount() {
        String email = "email-to-search";

        Account accountToSave = new Account();
        accountToSave.setEmail(email);
        accountToSave.setPassword("anyPassword");


        User userToSave = new User();
        userToSave.setName("anyName");
        userToSave = this.usersRepository.save(userToSave);

        accountToSave.setUserOwner(userToSave);
        this.accountsRepository.save(accountToSave);

        Optional<Account> accountFounded = this.accountsRepository.findByEmail(email);
        Assertions.assertTrue(accountFounded.isPresent());

        Account account = accountFounded.get();
        Assertions.assertNotNull(account);
        Assertions.assertEquals(email, account.getEmail());

    }

}
