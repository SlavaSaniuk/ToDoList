package by.beltelecom.todolist.security.authentication;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.Role;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.RuntimeNotFoundException;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.services.security.role.RoleService;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link DatabaseUserDetailsService} is implementation of {@link UserDetailsService} service bean.
 * Used to retrieve information about users from database.
 */
@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUserDetailsService.class);
    // Spring beans:
    private final AccountsService accountsService; // Service bean (Autowired via constructor);
    private final RoleService roleService;

    /**
     * Construct new {@link DatabaseUserDetailsService} security bean.
     * @param anAccountsService - accounts service bean.
     * @param aRoleService - user roles service bean.
     */
    public DatabaseUserDetailsService(AccountsService anAccountsService, RoleService aRoleService) {
        ArgumentChecker.nonNull(anAccountsService, "anAccountsService");
        ArgumentChecker.nonNull(aRoleService, "aRoleService");

        LOGGER.debug(SpringLogging.Creation.createBean(DatabaseUserDetailsService.class));

        // Maps arguments:
        this.accountsService = anAccountsService;
        this.roleService = aRoleService;
    }

    /**
     * Get {@link UserDetails} by account email.
     * Method load {@link Account} entity from db, then find account user application roles
     * and construct new {@link UserDetails} object.
     * @param username the username identifying the user whose data is required.
     * @return - user details.
     * @throws UsernameNotFoundException - Throws in cases, when account with specified email not founded in database
     * or account not referenced on user.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ArgumentChecker.nonNull(username, "username");

        try {
            LOGGER.debug(String.format("Find [Account] entity with email[%s] from database.", username));

            // Get account by email:
            Account account = this.accountsService.getAccountByEmail(username);

            // Get user application roles:
            List<Role> userRoles = this.roleService.findUserRoles(account.getUserOwner());

            // Create user authorities:
            List<GrantedAuthority> userAuthorities = new ArrayList<>();
            userRoles.forEach(role -> userAuthorities.add(new SimpleGrantedAuthority(role.getUserRole().getRoleName())));

            return new User(account.getEmail(), account.getPassword(), userAuthorities);
        }catch (RuntimeNotFoundException | NotFoundException exc) {
            throw new UsernameNotFoundException(String.format("[Account] with email[%s] not founded in database (or account not referenced on user entity).", username));
        }
    }
}
