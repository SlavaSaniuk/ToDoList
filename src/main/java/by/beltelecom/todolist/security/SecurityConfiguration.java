package by.beltelecom.todolist.security;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.security.authentication.*;
import by.beltelecom.todolist.security.rest.filters.CustomCorsFilter;
import by.beltelecom.todolist.security.rest.filters.JsonWebTokenFilter;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenService;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenServiceImpl;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.services.users.UsersService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Objects;

/**
 * Spring security configuration class. Class declare a various bean for requests authorization and users registration.
 * The core of security logic declared in {@link SignService} service bean.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private AccountsService accountsService; // Service bean (autowired via setter);
    private UsersService usersService; // Service bean (autowired via setter);
    private SecurityProperties securityProperties; // Configuration bean (autowired via setter);
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class); // Logger;

    /**
     * Construct new security configuration service bean.
     */
    public SecurityConfiguration() {
        LOGGER.debug(SpringLogging.Creation.createBean(SecurityConfiguration.class));
    }

    /**
     * Spring security configuration class for REST HTTP API.
     */
    @Order(1)
    @Configuration
    public static class RestSecurityConfiguration {

        private static final Logger LOGGER = LoggerFactory.getLogger(RestSecurityConfiguration.class); //Logger;

        private final JsonWebTokenFilter jsonWebTokenFilter; // Security filter bean.

        /**
         * Construct new {@link RestSecurityConfiguration} configuration bean.
         * @param aJsonWebTokenFilter - Security filter bean.
         */
        @Autowired
        public RestSecurityConfiguration(JsonWebTokenFilter aJsonWebTokenFilter) {
            Objects.requireNonNull(aJsonWebTokenFilter,
                    Checks.argumentNotNull("aJsonWebTokenFilter", JsonWebTokenFilter.class));
            LOGGER.debug(SpringLogging.Creation.createBean(RestSecurityConfiguration.class));
            this.jsonWebTokenFilter = aJsonWebTokenFilter;
        }

        /**
         * Defines a filter chain which is capable of being matched against an HttpServletRequest.
         * In order to decide whether it applies to that request.
         * @param http - {@link HttpSecurity} security bean.
         * @return - {@link  SecurityFilterChain} configuration bean.
         * @throws Exception - If any exception occurs.
         */
        @Bean
        public SecurityFilterChain restFilterChain(HttpSecurity http) throws Exception {

            // Disable CSRF security:
            http.csrf().disable();

            // Configure requests security
            http.authorizeRequests()
                    .antMatchers("/rest/sign/**").permitAll()
                    .antMatchers("/rest/**").authenticated();

            // Allow basic authentication
            http.httpBasic();

            // Set filters:
            http.addFilterBefore(this.jsonWebTokenFilter, UsernamePasswordAuthenticationFilter.class);
            http.addFilterBefore(this.customCorsFilter(), JsonWebTokenFilter.class);

            return http.build();
        }

        /**
         * {@link CustomCorsFilter} Spring HTTP filter bean user to allow CORS in application.
         * @return - Spring HTTP CORS filter.
         */
        @Bean
        public CustomCorsFilter customCorsFilter() {
            LOGGER.debug(SpringLogging.Creation.createBean(CustomCorsFilter.class));
            return new CustomCorsFilter();
        }

    }

    @Bean
    public JsonWebTokenFilter jsonWebTokenFilter() {
        LOGGER.debug(SpringLogging.Creation.createBean(JsonWebTokenFilter.class));
        return new JsonWebTokenFilter(this.jsonWebTokenService(), this.userDetailsService());
    }

    @Bean("jsonWebTokenService")
    @Primary
    public JsonWebTokenService jsonWebTokenService() {
        LOGGER.debug(SpringLogging.Creation.createBean(JsonWebTokenService.class));
        return new JsonWebTokenServiceImpl(this.securityProperties.getJwt());
    }

    /**
     * {@link SignService} security service bean used to register and authenticate users in application.
     */
    @Bean("signService")
    public SignService signService() {
        LOGGER.debug(SpringLogging.Creation.createBean(SignService.class));
        return new SignServiceImpl(this.accountsService, this.passwordEncoder(), this.usersService, this.authenticationManager());
    }

    /**
     * Database implementation of {@link AuthenticationManager} service bean.
     * Used to authenticate request and save user in {@link org.springframework.security.core.context.SecurityContext} context.
     */
    @Bean
    @Primary
    public AuthenticationManager authenticationManager() {
        LOGGER.debug(SpringLogging.Creation.createBean(AuthenticationManager.class));
        return new DatabaseAuthenticationManager(this.authenticationProvider());
    }

    /**
     * Database implementation of {@link  AuthenticationProvider} class.
     * {@link DatabaseAuthenticationProvider#authenticate(Authentication)} method to used to
     * compare specified password and db password.
     */
    @Bean
    @Primary
    public AuthenticationProvider authenticationProvider() {
        LOGGER.debug(SpringLogging.Creation.createBean(AuthenticationProvider.class));
        return new DatabaseAuthenticationProvider(this.userDetailsService(), this.passwordEncoder());
    }

    /**
     * {@link DatabaseUserDetailsService} is implementation of {@link UserDetailsService} service bean.
     * Used to retrieve information about users from database.
     */
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        LOGGER.debug(SpringLogging.Creation.createBean(UserDetailsService.class));
        return new DatabaseUserDetailsService(this.accountsService);
    }

    /**
     * Service interface for encoding passwords. The preferred implementation is BCryptPasswordEncoder.
     * @return - {@link PasswordEncoder} service bean.
     */
    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * {@link CredentialsValidator} security service bean used to validate accounts properties (e.g. mail, password, username).
     * @return - {@link CredentialsValidator} security configuration bean.
     */
    @Bean
    public CredentialsValidator credentialsValidator() {
        LOGGER.debug(SpringLogging.Creation.createBean(CredentialsValidator.class));
        return new CredentialsValidatorImpl(this.securityProperties);
    }

    /**
     * Autowire {@link AccountsService} service bean.
     * @param anAccountsService - service bean.
     */
    @Autowired
    public void setAccountsService(AccountsService anAccountsService) {
        LOGGER.debug(SpringLogging.Autowiring.autowireInConfiguration(AccountsService.class, SecurityConfiguration.class));
        this.accountsService = anAccountsService;
    }

    /**
     * Autowire {@link UsersService} service bean.
     * @param aUsersService - service bean.
     */
    @Autowired
    public void setUsersService(UsersService aUsersService) {
        LOGGER.debug(SpringLogging.Autowiring.autowireInConfiguration(UsersService.class, SecurityConfiguration.class));
        this.usersService = aUsersService;
    }

    /**
     * Autowire {@link SecurityProperties} configuration bean.
     * @param aSecurityProperties - configuration bean.
     */
    @Autowired
    public void setSecurityProperties(SecurityProperties aSecurityProperties) {
        LOGGER.debug(SpringLogging.Autowiring.autowireInConfiguration(SecurityProperties.class, SecurityConfiguration.class));
        this.securityProperties = aSecurityProperties;
    }

}
