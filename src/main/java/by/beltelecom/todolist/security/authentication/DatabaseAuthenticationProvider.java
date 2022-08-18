package by.beltelecom.todolist.security.authentication;

import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Database implementation of {@link  AuthenticationProvider} class.
 * {@link DatabaseAuthenticationProvider#authenticate(Authentication)} method to used to
 * compare specified password and db password.
 */
@Service
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService; // Spring bean;
    private final PasswordEncoder passwordEncoder; // Spring bean;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseAuthenticationProvider.class); // Logger;

    /**
     * Construct new {@link  DatabaseAuthenticationProvider} spring bean.
     * @param aDetailsService - {@link UserDetailsService} service bean;
     * @param aEncoder - {@link  PasswordEncoder} service bean;
     */
    public DatabaseAuthenticationProvider(UserDetailsService aDetailsService, PasswordEncoder aEncoder) {
        LOGGER.debug(SpringLogging.Creation.createBean(DatabaseAuthenticationProvider.class));
        // Check parameters:
        Objects.requireNonNull(aDetailsService, Checks.argumentNotNull("detailsService", UserDetailsService.class));
        Objects.requireNonNull(aEncoder, Checks.argumentNotNull("aEncoder", PasswordEncoder.class));

        // Maps parameters:
        this.userDetailsService = aDetailsService;
        this.passwordEncoder = aEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Objects.requireNonNull(authentication, Checks.argumentNotNull("authentication", Authentication.class));
        Objects.requireNonNull(authentication.getCredentials(),
                Checks.propertyOfArgumentNotNull("credentials", "authentication", Authentication.class));

        // Get user details:
        UserDetails details = this.userDetailsService.loadUserByUsername(authentication.getName());

        // Match passwords:
        if(!this.passwordEncoder.matches((String) authentication.getCredentials(), details.getPassword()))
            throw new BadCredentialsException("Password are not same.");

        // Return new Authentication token
        return new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
