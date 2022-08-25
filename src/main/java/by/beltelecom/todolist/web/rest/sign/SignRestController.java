package by.beltelecom.todolist.web.rest.sign;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import by.beltelecom.todolist.web.ExceptionStatusCodes;
import by.beltelecom.todolist.web.dto.rest.SignRestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/rest/sign", produces = "application/json")
@CrossOrigin(origins = "*")
public class SignRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignRestController.class); // Logger;
    private final SignService signService; // Spring security bean (Autowired in constructor);

    public SignRestController(SignService aSignService) {
        // Check parameters:
        Objects.requireNonNull(aSignService, Checks.argumentNotNull("aSignService", SignService.class));

        LOGGER.debug(SpringLogging.Creation.createBean(SignRestController.class));

        // Map arguments:
        this.signService = aSignService;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public SignRestDto logInAccount(@RequestBody Account account) {
        LOGGER.debug("Try to login account via SignRestController#logInAccount;");

        try {
            Account loggedAccount = this.signService.loginAccount(account);
            return new SignRestDto(loggedAccount.getUserOwner().getId());
        }catch (BadCredentialsException exc) {
            return new SignRestDto(ExceptionStatusCodes.BAD_CREDENTIALS_EXCEPTION);
        }
    }


}
