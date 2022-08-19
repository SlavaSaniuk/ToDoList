package by.beltelecom.todolist.web.controllers.users;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.users.UsersService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

/**
 * Controller class. Used to handle http request specific to user's page.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final UsersService usersService; // Autowired in constructor;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class); // Logger;

    /**
     * Construct new {@link UserController} controller bean.
     */
    public UserController(UsersService aUsersService) {
        LOGGER.debug(SpringLogging.Creation.createBean(UserController.class));

        // Check's args:
        Objects.requireNonNull(aUsersService, Checks.argumentNotNull("aUsersService", UsersService.class));

        // Maps args:
        this.usersService = aUsersService;
    }

    /**
     * Method handle http GET request to access user's page.
     * @param aId - user id;
     * @return {@link ModelAndView} object, with view: users/user.html.
     */
    @GetMapping("/{id}")
    public ModelAndView getAnyUserPage(@PathVariable("id") long aId) {
        ModelAndView modelAndView = new ModelAndView("users/user");

        // Get used by ID:
        User user = new User();
        try {
            user = this.usersService.getUserById(aId);
        }catch (NotFoundException exc) {
            LOGGER.warn(exc.getMessage());
            modelAndView.setViewName("redirect:/");
        }catch (IllegalArgumentException exc) {
            LOGGER.warn(Checks.Numbers.argNotZero("aId", Long.class));
            modelAndView.setViewName("redirect:/");
        }

        modelAndView.addObject("user", user);
        return modelAndView;
    }


}
