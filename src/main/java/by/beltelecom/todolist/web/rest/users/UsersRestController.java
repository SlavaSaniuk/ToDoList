package by.beltelecom.todolist.web.rest.users;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.users.UsersService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import by.beltelecom.todolist.web.ExceptionStatusCodes;
import by.beltelecom.todolist.web.dto.rest.UserRestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class UsersRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersRestController.class);

    private final UsersService usersService; // Service bean (autowired in constructor);

    public UsersRestController(UsersService aUsersService) {
        Objects.requireNonNull(aUsersService, Checks.argumentNotNull("aUsersService", UsersService.class));
        LOGGER.debug(SpringLogging.Creation.createBean(UsersRestController.class));
        this.usersService = aUsersService;
    }

    @GetMapping("/{userId}")
    public UserRestDto userById(@PathVariable("userId") long aId) {
        LOGGER.debug("Try to get user by id via UsersRestController#userById;");

        try {
            User user = this.usersService.getUserById(aId);
            return new UserRestDto(user);
        }catch (NotFoundException exc) {
            return new UserRestDto(ExceptionStatusCodes.NOT_FOUND_EXCEPTION);
        }

    }

}
