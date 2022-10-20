package by.beltelecom.todolist.security.authentication;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.exceptions.RuntimeNotFoundException;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.utilities.logging.Checks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

/**
 * {@link DatabaseUserDetailsService} is implementation of {@link UserDetailsService} service bean.
 * Used to retrieve information about users from database.
 */
@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final AccountsService accountsService; // Service bean (Autowired via constructor);
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUserDetailsService.class);

    public DatabaseUserDetailsService(AccountsService anAccountsService) {
        Objects.requireNonNull(anAccountsService, Checks.argumentNotNull("acAccountsService", AccountsService.class));
        // Maps arguments:
        this.accountsService = anAccountsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Try to get [Account] entity with email[{}] from database.", username);

        // Get account from database:
        Account account;

        try {
            account = this.accountsService.getAccountByEmail(username);
        }catch (RuntimeNotFoundException exc) {
            throw new UsernameNotFoundException(String.format("[Account] with email[%s] not founded in database.", username));
        }

        return new User(account.getEmail(), account.getPassword(), new ArrayList<>());
    }
}
