package by.beltelecom.todolist.web.rest.sign;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.AccountAlreadyRegisteredException;
import by.beltelecom.todolist.exceptions.PasswordNotValidException;
import by.beltelecom.todolist.security.authentication.CredentialsValidator;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenService;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@link SignRestController} controller bean handle HTTP REST request for login, register accounts
 * and getting validation rules.
 */
@RestController
@RequestMapping(value = "/rest/sign", produces = "application/json")
@CrossOrigin(origins = "*")
public class SignRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignRestController.class); // Logger;
    private final SignService signService; // Spring security bean (Autowired in constructor);
    private final CredentialsValidator credentialsValidator; // Spring security bean (Autowired in constructor);
    private final JsonWebTokenService jsonWebTokenService; // Security service bean (mapped in constructor);

    /**
     * Construct new {@link SignRestController} REST HTTP Controller bean.
     * @param aSignService - {@link SignService} service bean.
     * @param aCredentialsValidator - {@link CredentialsValidator} security service bean.
     */
    public SignRestController(SignService aSignService, CredentialsValidator aCredentialsValidator,
                              JsonWebTokenService aJsonWebTokenService) {
        // Check parameters:
        Objects.requireNonNull(aSignService,
                Checks.argumentNotNull("aSignService", SignService.class));
        Objects.requireNonNull(aCredentialsValidator,
                Checks.argumentNotNull("aCredentialsValidator", CredentialsValidator.class));
        Objects.requireNonNull(aJsonWebTokenService,
                Checks.argumentNotNull("aJsonWebTokenService", JsonWebTokenService.class));

        LOGGER.debug(SpringLogging.Creation.createBean(SignRestController.class));

        // Map arguments:
        this.signService = aSignService;
        this.credentialsValidator = aCredentialsValidator;
        this.jsonWebTokenService = aJsonWebTokenService;
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

            // Generate JWT and return:
            return new SignRestDto(loggedAccount.getUserOwner().getId(),
                    this.jsonWebTokenService.generateToken(loggedAccount.getEmail()));
        }catch (BadCredentialsException exc) {
            return new SignRestDto(ExceptionStatusCodes.BAD_CREDENTIALS_EXCEPTION);
        }
    }

    /**
     * Method handle REST POST HTTP requests to register user account.
     * If account successfully registerd method return {@link SignRestDto} object with initialized userId property.
     * In other cases method return {@link SignRestDto} object with initialized {@link SignRestDto#getExceptionCode()}
     * and {@link SignRestDto#isException()} properties. Method handle {@link AccountAlreadyRegisteredException}
     * and {@link PasswordNotValidException} exceptions.
     * @param accountUserDto - {@link AccountUserDto} to register;
     * @return - {@link SignRestDto} object.
     */
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
            // Generate JWT and return:
            return new SignRestDto(account.getUserOwner().getId(),
                    this.jsonWebTokenService.generateToken(account.getEmail()));

        }catch (AccountAlreadyRegisteredException exc) {
            return new SignRestDto(ExceptionStatusCodes.ACCOUNT_ALREADY_REGISTERED_EXCEPTION);
        }catch (PasswordNotValidException exc) {
            return new SignRestDto(ExceptionStatusCodes.PASSWORD_NOT_VALID_EXCEPTION);
        }
    }

    /**
     * Method handle REST GET HTTP requests for getting password validation rules (e.g. properties).
     * @return - {@link Map} password validation rules.
     */
    @GetMapping(value = "/validation_rules_passwords", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> passwordValidationRules() {
        LOGGER.debug("Try to get passwords validation rules;");
        Map<String, Object> passwordValidationRules = new HashMap<>();

        passwordValidationRules.put("minLength", this.credentialsValidator.validationRules().get("to.do.security.passwords.min-length"));
        passwordValidationRules.put("isUseNumbers", this.credentialsValidator.validationRules().get("to.do.security.passwords.use-numbers"));
        passwordValidationRules.put("isUseUppercaseLetters", this.credentialsValidator.validationRules().get("to.do.security.passwords.use-uppercase-letter"));
        passwordValidationRules.put("isUseSpecialSymbols", this.credentialsValidator.validationRules().get("to.do.security.passwords.use-special-symbols"));

        return passwordValidationRules;
    }
}
