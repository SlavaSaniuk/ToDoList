package by.beltelecom.todolist.security.rest.filters;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenService;
import by.beltelecom.todolist.services.security.AccountsService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * {@link JsonWebTokenFilter} security filter bean intercept incoming http requests
 * and check JWT in "Authorization" header. If JWT is not exist or invalid, return http response with 403 error code
 * (Forbidden). Except cases, when request try to access "/rest/sign/**" urls.
 */
public class JsonWebTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebTokenFilter.class); // Logger;

    private final JsonWebTokenService tokenService; // JWT Security service;
    private final UserDetailsService detailsService; // UserDetails security service;

    private final AccountsService accountsService; // Accounts service;

    /**
     * Construct new {@link JsonWebTokenFilter} security filter bean.
     * @param jsonWebTokenService - JWT security service bean.
     * @param userDetailsService - Security service bean.
     */
    @Autowired
    public JsonWebTokenFilter(JsonWebTokenService jsonWebTokenService, UserDetailsService userDetailsService, AccountsService anAccountsService) {
        Objects.requireNonNull(jsonWebTokenService,
                Checks.argumentNotNull("jsonWebTokenService", JsonWebTokenService.class));
        Objects.requireNonNull(userDetailsService,
                Checks.argumentNotNull("userDetailsService", UserDetailsService.class));
        Objects.requireNonNull(anAccountsService,
                Checks.argumentNotNull("anAccountsService", AccountsService.class));
        LOGGER.debug(SpringLogging.Creation.createBean(JsonWebTokenFilter.class));

        this.tokenService = jsonWebTokenService;
        this.detailsService = userDetailsService;
        this.accountsService = anAccountsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Check request method:
        String method = request.getMethod();
        if (!method.equals("GET") && !method.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get "AUTHORIZATION" header and check it:
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isBlank() || !authorizationHeader.startsWith("Beaver ")) {
            if (request.getRequestURI().substring(request.getContextPath().length()).startsWith("/rest/sign")){
                filterChain.doFilter(request, response);
                return;
            }
            else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        // Get JsonWebToken and check it:
        String JWT = authorizationHeader.substring(7);
        if (JWT.isEmpty()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Validate token and get email:
        String accountEmail = this.tokenService.verifyToken(JWT);
        // Get user details and create username token:
        if (accountEmail == null || accountEmail.isEmpty()) response.sendError(HttpServletResponse.SC_FORBIDDEN);
        try {
            UserDetails details = this.detailsService.loadUserByUsername(accountEmail);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details.getUsername(), details.getPassword(), details.getAuthorities());

            // Set authentication:
            if (SecurityContextHolder.getContext().getAuthentication() == null)
                SecurityContextHolder.getContext().setAuthentication(token);

            // Add user entity to request:
            User userObj = this.accountsService.getAccountByEmail(accountEmail).getUserOwner();
            request.setAttribute("userObj", userObj);

            //Do filter:
            filterChain.doFilter(request, response);
        }catch (UsernameNotFoundException exc) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }


    }

}
