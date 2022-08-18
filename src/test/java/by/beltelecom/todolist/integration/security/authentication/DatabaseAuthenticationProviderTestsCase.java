package by.beltelecom.todolist.integration.security.authentication;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.security.SignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@DataJpaTest
public class DatabaseAuthenticationProviderTestsCase {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private SignService signService;

    @Test
    void authenticate_emailIsNull_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(null, "anyCredentials")));
    }

    @Test
    void authenticate_credentialsIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class,
                ()-> this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("anyName", null)));
    }

    @Test
    void authenticate_emailNotFound_shouldThrowUNFE() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                ()-> this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("anyName", "anyPassword")));
    }

    @Test
    void authenticate_passwordAneNotSame_shouldThrowBCE() {
        Account account = new Account();
        account.setEmail("anyEmail");
        account.setPassword("pass");
        User user = new User();
        user.setName("anyName");
        signService.registerAccount(account, user);

        Assertions.assertThrows(BadCredentialsException.class,
                ()-> this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("anyEmail", "anyPassword")));

    }

    @Test
    void authenticate_credentialsAreSame_shouldReturnNewAuthentication() {
        Account account = new Account();
        account.setEmail("anyEmail");
        account.setPassword("anyPassword");
        User user = new User();
        user.setName("anyName");
        signService.registerAccount(account, user);

        Assertions.assertNotNull(
                this.authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken("anyEmail", "anyPassword")));

    }


}
