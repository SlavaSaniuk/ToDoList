package by.beltelecom.todolist.web.rest.administration;

import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.data.models.Role;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.security.role.RoleService;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/administration/")
public class AdministrationRestController {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministrationRestController.class);
    // Spring beans:
    private final RoleService roleService;

    /**
     * Construct new {@link AdministrationRestController} REST HTTP controller.
     * @param aRoleService - role service bean.
     */
    @Autowired
    public AdministrationRestController(RoleService aRoleService) {
        // Check arguments:
        ArgumentChecker.nonNull(aRoleService, "aRoleService");

        LOGGER.debug(SpringLogging.Creation.createBean(AdministrationRestController.class));

        // Map arguments:
        this.roleService = aRoleService;
    }

    @GetMapping(value = "/hello", produces = "text/html")
    public ResponseEntity<String> helloAdmin(@RequestAttribute("userObj") User userObj) {
        String helloString = String.format("Hello, administrator %s!", userObj.getName());
        return new ResponseEntity<>(helloString, HttpStatus.OK);
    }

    /**
     * Method handle http get request on "/rest/administration/user-roles" url to get user role.
     * User argument is request attribute which initialized in
     * {@link by.beltelecom.todolist.security.rest.filters.JsonWebTokenFilter} filter based on "Authorization"
     * request header.
     * Application user can get list only of his roles.
     * @param userObj - user which send request.
     * @return - list of user roles.
     */
    @GetMapping(value = "/user-roles", consumes = "application/json", produces = "application/json")
    public ResponseEntity userRoles(@RequestAttribute("userObj") User userObj) {
        LOGGER.debug(String.format("Try to get list of user[%s] roles;", userObj));

        try {
            List<UserRole> userRoles = new ArrayList<>();

            // Get user role entities:
            List<Role> roles = this.roleService.findUserRoles(userObj);
            roles.forEach(role -> userRoles.add(role.getUserRole()));

            return ResponseEntity.status(HttpStatus.OK).body(userRoles);
        } catch (NotFoundException e) {
            LOGGER.warn(e.getMessage());
            return new ResponseEntity.;
        }
    }

}
