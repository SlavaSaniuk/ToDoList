package by.beltelecom.todolist.web.controllers.users;

import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class. Used to handle http request specific to user's page.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class); // Logger;

    /**
     * Construct new {@link UserController} controller bean.
     */
    public UserController() {
        LOGGER.debug(SpringLogging.Creation.createBean(UserController.class));
    }

    /**
     * Method handle http GET request to access user's page.
     * @param aId - user id;
     * @return {@link ModelAndView} object, with view: users/user.html.
     */
    @GetMapping("/{id}")
    public ModelAndView getUserPage(@PathVariable("id") long aId) {
        ModelAndView modelAndView = new ModelAndView("users/user");


        return modelAndView;
    }


}
