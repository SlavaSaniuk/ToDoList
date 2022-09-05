package by.beltelecom.todolist.security.rest.filters;

import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@link CustomCorsFilter} Spring HTTP filter bean user to allow CORS in application.
 * Filter add necessary CORS headers in every HTTP response. If method "OPTIONS", filter allow and break request and
 * return response with 200 status code.
 */
public class CustomCorsFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomCorsFilter.class); // Logger;

    public CustomCorsFilter() {
        LOGGER.debug(SpringLogging.Creation.createBean(CustomCorsFilter.class));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Add allowing CORS response headers:
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // If methods OPTIONS, allow
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Do filter:
        filterChain.doFilter(request, response);
    }

}
