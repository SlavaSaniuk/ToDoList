package by.beltelecom.todolist.security.authentication;

import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Database implementation of {@link AuthenticationManager} service bean.
 * Used to authenticate request and save user in {@link org.springframework.security.core.context.SecurityContext} context.
 */
@Service
public class DatabaseAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider; // Security service bean;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseAuthenticationManager.class);

    /**
     * Construct new DatabaseAuthenticationManager service bean.
     * @param anAuthenticationProvider - authentication provider;
     */
    public DatabaseAuthenticationManager(AuthenticationProvider anAuthenticationProvider) {
        LOGGER.debug(SpringLogging.Creation.createBean(DatabaseAuthenticationManager.class));

        // Check arguments:
        Objects.requireNonNull(anAuthenticationProvider,
                Checks.argumentNotNull("anAuthenticationProvider", AuthenticationProvider.class));

        // Maps parameters:
        this.authenticationProvider = anAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            authentication = this.authenticationProvider.authenticate(authentication);
        }catch (UsernameNotFoundException | BadCredentialsException exc) {
            throw new BadCredentialsException(exc.getMessage());
        }

        // If request authenticated, put it in SecurityContext:
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }

}
