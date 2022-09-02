package by.beltelecom.todolist.integration.serviecs.users;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.services.users.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UsersServiceTestsCase {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private SignService signService;

    @Autowired
    private UsersService usersService;

    @Test
    void createUser_nameString_shouldReturnCreatedUser() {
        String name = "testName";
        User user = this.usersService.createUser(name);

        Assertions.assertNotNull(user);
        Assertions.assertNotEquals(0L, user.getId());
        Assertions.assertEquals(name, user.getName());
    }

    @Test
    void getUserById_userNotFound_shouldThrowNFE() {
        Assertions.assertThrows(NotFoundException.class, () -> this.usersService.getUserById(5L));
    }

    @Test
    void getUserById_userFounded_shouldReturnUser() {
        String name = "testName";
        User user = this.usersService.createUser(name);
        long createdId = user.getId();

        User founded = this.usersService.getUserById(createdId);

        Assertions.assertNotNull(founded);
        Assertions.assertEquals(createdId, founded.getId());
        Assertions.assertEquals(user.getName(), founded.getName());
    }

    @Test
    void deleteUser_userIsCreated_shouldDeleteUserWithAccount() {
        Account account = new Account();
        account.setEmail("anyEmail@mail.it");
        account.setPassword("111");

        User user = new User();
        user.setName("Any name");

        Account createdAccount = this.signService.registerAccount(account, user);
        User createdUser = createdAccount.getUserOwner();
        long userId = createdUser.getId();

        this.usersService.deleteUser(createdUser);

        Assertions.assertThrows(NotFoundException.class, () -> this.usersService.getUserById(userId));
        Assertions.assertThrows(NotFoundException.class, () -> this.accountsService.getAccountByEmail(account.getEmail()));
    }

    @Test
    void deleteUser_createUsersAndAccount20times_shouldDeleteAllUsersWithAccounts() {
        for (int i = 0; i < 20; i++) {


            Account account = new Account();
            account.setEmail("anyEmail@mail.it");
            account.setPassword("111");

            User user = new User();
            user.setName("Any name");

            Account createdAccount = this.signService.registerAccount(account, user);
            User createdUser = createdAccount.getUserOwner();
            long userId = createdUser.getId();

            this.usersService.deleteUser(createdUser);

            Assertions.assertThrows(NotFoundException.class, () -> this.usersService.getUserById(userId));
            Assertions.assertThrows(NotFoundException.class, () -> this.accountsService.getAccountByEmail(account.getEmail()));
        }
    }
}
