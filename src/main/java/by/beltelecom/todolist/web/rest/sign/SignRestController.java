package by.beltelecom.todolist.web.rest.sign;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.AccountAlreadyRegisteredException;
import by.beltelecom.todolist.exceptions.PasswordNotValidException;
import by.beltelecom.todolist.security.authentication.CredentialsValidator;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import by.beltelecom.todolist.web.ExceptionStatusCodes;
import by.beltelecom.todolist.web.dto.AccountUserDto;
import by.beltelecom.todolist.web.dto.rest.SignRestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/rest/sign", produces = "application/json")
@CrossOrigin(origins = "*")
public class SignRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignRestController.class); // Logger;
    private final SignService signService; // Spring security bean (Autowired in constructor);
    private final CredentialsValidator credentialsValidator; // Spring security bean (Autowired in constructor);

    /**
     * Construct new {@link SignRestController} REST HTTP Controller bean.
     * @param aSignService - {@link SignService} service bean.
     * @param aCredentialsValidator - {@link CredentialsValidator} security service bean.
     */
    public SignRestController(SignService aSignService, CredentialsValidator aCredentialsValidator) {
        // Check parameters:
        Objects.requireNonNull(aSignService, Checks.argumentNotNull("aSignService", SignService.class));
        Objects.requireNonNull(aCredentialsValidator, Checks.argumentNotNull("aCredentialsValidator", CredentialsValidator.class));

        LOGGER.debug(SpringLogging.Creation.createBean(SignRestController.class));

        // Map arguments:
        this.signService = aSignService;
        this.credentialsValidator = aCredentialsValidator;
    }

    /**
     * Method handle REST HTTP requests to log in user account.
     * If account successfully authenticated method return {@link SignRestDto} object with initialized userId property.
     * In other cases method return {@link SignRestDto} object with initialized {@link SignRestDto#getExceptionCode()}
     * and {@link SignRestDto#isException()} properties. Method handle {@link BadCredentialsException} exception.
     * When BCE occurs {@link SignRestDto#getExceptionCode()} has {@link ExceptionStatusCodes#BAD_CREDENTIALS_EXCEPTION} 601 status code.
     * @param account - {@link Account} to login;
     * @return - {@link SignRestDto} object.
     */
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

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public SignRestDto registerAccount(@RequestBody AccountUserDto accountUserDto) {
        LOGGER.debug("Try to register account via SignRestController#registerAccount;");

        try {
            Account account = accountUserDto.toEntity();
            User user = accountUserDto.toUser();

            // Validate account:
            this.credentialsValidator.validate(account);

            // Register account:
            account = this.signService.registerAccount(account, user);
            return new SignRestDto(account.getUserOwner().getId());

        }catch (AccountAlreadyRegisteredException exc) {
            return new SignRestDto(ExceptionStatusCodes.ACCOUNT_ALREADY_REGISTERED_EXCEPTION);
        }catch (PasswordNotValidException exc) {
            return new SignRestDto(ExceptionStatusCodes.PASSWORD_NOT_VALID_EXCEPTION);
        }
    }
}
