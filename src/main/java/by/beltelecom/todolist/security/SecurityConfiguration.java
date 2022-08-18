package by.beltelecom.todolist.security;

import by.beltelecom.todolist.security.authentication.DatabaseAuthenticationManager;
import by.beltelecom.todolist.security.authentication.DatabaseAuthenticationProvider;
import by.beltelecom.todolist.security.authentication.DatabaseUserDetailsService;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.security.authentication.SignServiceImpl;
import by.beltelecom.todolist.services.security.UsersService;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private AccountsService accountsService; // Service bean (autowired via setter);

    private UsersService usersService; // Service bean (autowired via setter);
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/sign/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/sign");

        return  security.build();
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

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void setAccountsService(AccountsService anAccountsService) {
        LOGGER.debug(SpringLogging.Autowiring.autowireInConfiguration(AccountsService.class, SecurityConfiguration.class));
        this.accountsService = anAccountsService;
    }

    @Autowired
    public void setUsersService(UsersService aUsersService) {
        LOGGER.debug(SpringLogging.Autowiring.autowireInConfiguration(UsersService.class, SecurityConfiguration.class));
        this.usersService = aUsersService;
    }

}
