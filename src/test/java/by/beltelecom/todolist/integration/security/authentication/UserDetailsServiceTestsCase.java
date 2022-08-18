package by.beltelecom.todolist.integration.security.authentication;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.security.authentication.SignService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserDetailsServiceTestsCase {

    @Autowired
    private SignService signService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    void loadUserByUsername_userNotFound_shouldThrowUNFE() {
        Assertions.assertThrows(UsernameNotFoundException.class, ()-> this.userDetailsService.loadUserByUsername("anyEmail"));
    }

    @Test
    void loadUserByUsername_userFound_shouldReturnUserDetails() {
        Account account = new Account();
        account.setEmail("anyEmail");
        account.setPassword("anyPassword");
        User user = new User();
        user.setName("anyName");
        signService.registerAccount(account, user);

        UserDetails founded = this.userDetailsService.loadUserByUsername("anyEmail");
        Assertions.assertNotNull(founded);
        Assertions.assertEquals(account.getEmail(), founded.getUsername());
        Assertions.assertNotNull(founded.getPassword());
    }

}
