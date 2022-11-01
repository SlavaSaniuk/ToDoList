package by.beltelecom.todolist.integration.security.authentication;

import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.services.security.role.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ServicesTestsConfiguration.class)
public class UserDetailsServiceTestsCase {

    @Autowired
    private SignService signService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TestsUserService testsUserService;
    @Autowired
    private RoleService roleService;

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

    @Test
    void loadUserByUsername_userDoesNotHaveAnyRoles_shouldReturnUserDetailsWithEmptyGrantedAuthoritiesList() {
        // Create account:
        Account account = this.testsUserService.testingUser("loadUserBuUsername1").getAccount();

        // Load details:
        UserDetails details = this.userDetailsService.loadUserByUsername(account.getEmail());
        Assertions.assertNotNull(details);
        Assertions.assertNotNull(details.getAuthorities());
        Assertions.assertTrue(details.getAuthorities().isEmpty());
    }

    @Test
    void loadUserByUsername_userHaveRole_shouldReturnUserDetailsWithGrantedAuthoritiesList() {
        // Create account:
        Account account = this.testsUserService.testingUser("loadUserBuUsername2").getAccount();
        // Add roles to user:
        try {
            this.roleService.addRoleToUser(UserRole.ROLE_ROOT_ADMIN, account.getUserOwner());
        } catch (NotFoundException e) {
            Assertions.fail();
        }

        // Load details:
        UserDetails details = this.userDetailsService.loadUserByUsername(account.getEmail());
        Assertions.assertNotNull(details);
        Assertions.assertNotNull(details.getAuthorities());
        Assertions.assertFalse(details.getAuthorities().isEmpty());

    }

}
