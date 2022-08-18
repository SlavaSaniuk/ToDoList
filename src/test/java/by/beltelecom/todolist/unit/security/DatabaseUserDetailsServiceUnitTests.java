package by.beltelecom.todolist.unit.security;

import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.security.authentication.DatabaseUserDetailsService;
import by.beltelecom.todolist.services.security.AccountsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DatabaseUserDetailsServiceUnitTests {

    @MockBean
    private AccountsService accountsService;

    private DatabaseUserDetailsService databaseUserDetailsService;

    @BeforeEach
    void beforeEach() {
        this.databaseUserDetailsService = new DatabaseUserDetailsService(accountsService);
    }

    @Test
    void loadUserByUsername_userNotFound_shouldThrowUNFE() {
        Mockito.when(this.accountsService.getAccountByEmail(ArgumentMatchers.anyString())).thenThrow(NotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> this.databaseUserDetailsService.loadUserByUsername("amyEmail"));
    }
}
