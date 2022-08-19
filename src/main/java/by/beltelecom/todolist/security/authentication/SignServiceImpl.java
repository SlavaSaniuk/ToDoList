package by.beltelecom.todolist.security.authentication;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.AccountAlreadyRegisteredException;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.services.users.UsersService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Default implementation of {@link SignService} service bean.
 */
public class SignServiceImpl implements SignService {

    private final AuthenticationManager authenticationManager; // (mapped in constructor);
    private final AccountsService accountsService; // Repository bean (mapped in constructor);
    private final PasswordEncoder passwordEncoder; // SecurityService bean (mapped in constructor);
    private final UsersService usersService; // Users service bean (mapped in constructor);
    private static final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    public SignServiceImpl(AccountsService anAccountsService, PasswordEncoder aPasswordEncoder,
                           UsersService aUsersService, AuthenticationManager anAuthenticationManager) {
        LOGGER.debug(SpringLogging.Creation.createBean(SignServiceImpl.class));

        // Check arguments:
        Objects.requireNonNull(anAuthenticationManager,
                Checks.argumentNotNull("anAuthenticationManager", AuthenticationManager.class));

        // Maps arguments
        this.accountsService = anAccountsService;
        this.passwordEncoder = aPasswordEncoder;
        this.usersService = aUsersService;
        this.authenticationManager = anAuthenticationManager;
    }

    @Override
    @Transactional
    public Account registerAccount(Account anAccount, User aUser) {
        Objects.requireNonNull(anAccount, "[Account] parameter must be not <null>.");
        Objects.requireNonNull(aUser, "[User] parameter must be not <null>.");
        LOGGER.debug("Try to register new [Account] entity.");

        // Check parameters:
        Objects.requireNonNull(anAccount.getEmail(), "[Email] field in [Account] parameter must be not <null>.");
        Objects.requireNonNull(anAccount.getPassword(), "[Password] field in [Account] parameter must be not <null>.");
        if (anAccount.getEmail().length() == 0)
            throw new IllegalArgumentException("[Email] field in [Account] parameter must be not empty.");
        if (anAccount.getPassword().length() == 0)
            throw new IllegalArgumentException("[Password] field in [Account] parameter must be not empty.");

        // Check if account already registered:
        if(this.accountsService.getAccountOptByEmail(anAccount.getEmail()).isPresent())
            throw new AccountAlreadyRegisteredException(anAccount);

        // Try to create new user with name:
        User createdUser = this.usersService.createUser(aUser.getName());
        createdUser.setUserAccount(anAccount);
        anAccount.setUserAccount(createdUser);

        // Encode password:
        anAccount.setPassword(this.passwordEncoder.encode(anAccount.getPassword()));

        // Save in database:
        return this.accountsService.saveAccount(anAccount);
    }

    @Override
    public Account loginAccount(Account anAccount) throws AuthenticationException {
        // Check parameters:
        Objects.requireNonNull(anAccount, Checks.argumentNotNull("anAccount", Account.class));

        // May throw BCE:
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(anAccount.getEmail(), anAccount.getPassword()));

        // If account authenticated, get account from DB:
        return this.accountsService.getAccountByEmail(anAccount.getEmail());
    }
}
