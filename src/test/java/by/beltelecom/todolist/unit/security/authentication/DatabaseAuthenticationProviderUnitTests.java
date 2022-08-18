package by.beltelecom.todolist.unit.security.authentication;

import by.beltelecom.todolist.security.authentication.DatabaseAuthenticationProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

@ExtendWith(SpringExtension.class)
public class DatabaseAuthenticationProviderUnitTests {

    private DatabaseAuthenticationProvider authenticationProvider;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void beforeEach() {
        this.authenticationProvider = new DatabaseAuthenticationProvider(this.userDetailsService, this.passwordEncoder);
    }

    @Test
    void authenticate_argIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> this.authenticationProvider.authenticate(null));
    }

    @Test
    void authenticate_emailNotFound_shouldThrowUNFE() {
        Mockito.when(this.userDetailsService.loadUserByUsername(ArgumentMatchers.anyString())).thenThrow(UsernameNotFoundException.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("anyEmail", "anyPassword");
        Assertions.assertThrows(UsernameNotFoundException.class, () -> this.authenticationProvider.authenticate(authenticationToken));
    }

    @Test
    void authenticate_emailIsNull_shouldThrowNPE() {
        Mockito.when(this.userDetailsService.loadUserByUsername(ArgumentMatchers.anyString())).thenThrow(NullPointerException.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("anyEmail", "anyPassword");
        Assertions.assertThrows(NullPointerException.class, () -> this.authenticationProvider.authenticate(authenticationToken));
    }

    @Test
    void authenticate_passwordIsNull_shouldThrowNPe() {
        User details = new User("anyEmail", "anyPassword", new ArrayList<>());
        Mockito.when(this.userDetailsService.loadUserByUsername(ArgumentMatchers.anyString())).thenReturn(details);


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("anyEmail", null);
        Assertions.assertThrows(NullPointerException.class, () -> this.authenticationProvider.authenticate(authenticationToken));

    }

    @Test
    void authenticate_passwordNotMatch_shouldThrowBCE() {
        User details = new User("anyEmail", "anyPassword", new ArrayList<>());
        Mockito.when(this.userDetailsService.loadUserByUsername(ArgumentMatchers.anyString())).thenReturn(details);

        Mockito.when(this.passwordEncoder.matches(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())).thenReturn(false);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("anyEmail", "");
        Assertions.assertThrows(BadCredentialsException.class, () -> this.authenticationProvider.authenticate(authenticationToken));
    }
}
