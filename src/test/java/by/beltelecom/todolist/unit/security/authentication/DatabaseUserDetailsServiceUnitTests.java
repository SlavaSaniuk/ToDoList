package by.beltelecom.todolist.unit.security.authentication;

import by.beltelecom.todolist.exceptions.RuntimeNotFoundException;
import by.beltelecom.todolist.security.authentication.DatabaseUserDetailsService;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.services.security.role.RoleService;
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
    @MockBean
    private RoleService roleService;

    private DatabaseUserDetailsService databaseUserDetailsService;

    @BeforeEach
    void beforeEach() {
        this.databaseUserDetailsService = new DatabaseUserDetailsService(accountsService, roleService);
    }

    @Test
    void loadUserByUsername_userNotFound_shouldThrowUNFE() {
        Mockito.when(this.accountsService.getAccountByEmail(ArgumentMatchers.anyString())).thenThrow(RuntimeNotFoundException.class);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> this.databaseUserDetailsService.loadUserByUsername("amyEmail"));
    }
}
