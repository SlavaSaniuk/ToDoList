package by.beltelecom.todolist.unit.services.security;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.AccountAlreadyRegisteredException;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.security.authentication.SignServiceImpl;
import by.beltelecom.todolist.services.security.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class SignServiceImplUnitTests {

    @MockBean
    private AccountsService accountsService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private UsersService usersService;

    @MockBean
    private AuthenticationManager authenticationManager;

    private SignServiceImpl signService;

    @BeforeEach
    void beforeEach() {
        this.signService = new SignServiceImpl(accountsService, passwordEncoder, usersService, authenticationManager);
    }

    @Test
    void registerAccount_anAccountIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, ()-> this.signService.registerAccount(null, new User()));
    }

    @Test
    void registerAccount_emailIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, ()-> this.signService.registerAccount(new Account(), new User()));
    }

    @Test
    void registerAccount_passwordIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, ()-> this.signService.registerAccount(new Account(), new User()));
    }

    @Test
    void registerAccount_accountAlreadyRegistered_shouldThrowAAR() {
        Account account = new Account();
        account.setEmail("any-email");
        account.setPassword("any-password");

        Mockito.when(this.accountsService.getAccountOptByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(account));

        Assertions.assertThrows(AccountAlreadyRegisteredException.class, () -> this.signService.registerAccount(account, new User()));
    }

    @Test
    void registerAccount_userParameterIsNull_shouldThrowNPE() {
        Account account = new Account();
        account.setEmail("anyEmail");
        account.setPassword("anyPassword");

        Assertions.assertThrows(NullPointerException.class, () -> this.signService.registerAccount(account, null));
    }

    @Test
    void registerAccount_userParameterNamePropertyIsNull_shouldThrowNPE() {
        Account account = new Account();
        account.setEmail("anyEmail");
        account.setPassword("anyPassword");

        User user = new User();

        Mockito.when(this.usersService.createUser(null)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> this.signService.registerAccount(account, user));
    }

    @Test
    void registerAccount_userParameterNamePropertyIsEmpty_shouldThrowIAE() {
        Account account = new Account();
        account.setEmail("anyEmail");
        account.setPassword("anyPassword");

        User user = new User();
        user.setName("");

        Mockito.when(this.usersService.createUser("")).thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class, () -> this.signService.registerAccount(account, user));
    }

    @Test
    void registerAccount_accountEmailIsEmpty_shouldThrowIAE() {
        Account account = new Account();
        account.setEmail("");
        account.setPassword("123");
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.signService.registerAccount(account, new User()));
    }

    @Test
    void registerAccount_accountPasswordIsEmpty_shouldThrowIAE() {
        Account account = new Account();
        account.setEmail("anyEmail");
        account.setPassword("");
        Assertions.assertThrows(IllegalArgumentException.class, ()-> this.signService.registerAccount(account, new User()));
    }

}
