package by.beltelecom.todolist.security.authentication;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.exceptions.PasswordNotValidException;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Default implementation of {@link CredentialsValidator} security service bean.
 */
public class CredentialsValidatorImpl implements CredentialsValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsValidatorImpl.class); // Logger;
    // Password validation rules:
    @Setter @Getter
    private int passwordMinLength;
    @Setter @Getter
    private boolean passwordShouldUseNumbers;
    @Setter @Getter
    private boolean passwordShouldUseUppercaseLetters;
    @Setter @Getter
    private boolean passwordShouldUseSpecialSymbols;

    /**
     * Construct new {@link CredentialsValidatorImpl} service bean.
     * @param aSecurityProperties - {@link SecurityProperties} security configuration properties.
     */
    public CredentialsValidatorImpl(SecurityProperties aSecurityProperties) {
        Objects.requireNonNull(aSecurityProperties, Checks.argumentNotNull("aSecurityProperties", SecurityProperties.class));

        LOGGER.debug(SpringLogging.Creation.createBean(CredentialsValidatorImpl.class));

        // Map properties:
        this.passwordMinLength = aSecurityProperties.getPasswords().getMinLength();
        this.passwordShouldUseNumbers = aSecurityProperties.getPasswords().isUseNumbers();
        this.passwordShouldUseUppercaseLetters = aSecurityProperties.getPasswords().isUseUppercaseLetter();
        this.passwordShouldUseSpecialSymbols = aSecurityProperties.getPasswords().isUseSpecialSymbols();
    }

    @Override
    public boolean validate(Account anAccount) {
        return false;
    }

    @Override
    public boolean validatePassword(String aPassword) throws PasswordNotValidException {
        Objects.requireNonNull(aPassword, Checks.argumentNotNull("aPassword", String.class));
        LOGGER.debug("Try to validate password;");

        // Check with rules:
        if (!this.passwordHasMinimumLength(aPassword))
            throw new PasswordNotValidException(ValidationRule.PASSWORD_MIN_LENGTH);
        if (!this.isContainNumbers(aPassword))
            throw new PasswordNotValidException(ValidationRule.PASSWORD_USE_NUMBERS);

        return true;
    }

    public boolean passwordHasMinimumLength(String aPassword) {
        return aPassword.length() >= this.passwordMinLength;
    }

    public boolean isContainNumbers(String aPassword) {
        return aPassword.matches(".*\\d.*");
    }
}
