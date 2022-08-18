package by.beltelecom.todolist.unit.services.security;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.repositories.AccountsRepository;
import by.beltelecom.todolist.services.security.AccountsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith({SpringExtension.class})
public class AccountsServiceImplUnitsTests {

    @MockBean
    private AccountsRepository accountsRepository;
    private AccountsServiceImpl accountsService;

    @BeforeEach
    void beforeEach() {
        this.accountsService = new AccountsServiceImpl(this.accountsRepository);
    }

    @Test
    void getAccountByEmail_emailIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> this.accountsService.getAccountByEmail(null));
    }

    @Test
    void getAccountByEmail_emailIsEmpty_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.accountsService.getAccountByEmail(""));
    }

    @Test
    void getAccountByEmail_accountNotFounded_shouldReturnEmptyOptional() {
        Mockito.when(this.accountsRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Assertions.assertFalse(this.accountsService.getAccountOptByEmail("any-email").isPresent());
    }

    @Test
    void getAccountByEmail_accountFounded_shouldReturnOptionalOfAccount() {
        Account account = new Account();
        Mockito.when(this.accountsRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(Optional.of(account));
        Assertions.assertTrue(this.accountsService.getAccountOptByEmail("any-email").isPresent());
    }
}
