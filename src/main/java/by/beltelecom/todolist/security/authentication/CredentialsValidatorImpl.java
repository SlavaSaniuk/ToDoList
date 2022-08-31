package by.beltelecom.todolist.security.authentication;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.exceptions.PasswordNotValidException;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Default implementation of {@link CredentialsValidator} security service bean.
 */
public class CredentialsValidatorImpl implements CredentialsValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsValidatorImpl.class); // Logger;
    // Password validation rules:
    @Setter
    private int passwordMinLength;
    @Setter
    private boolean passwordShouldUseNumbers;
    @Setter
    private boolean passwordShouldUseUppercaseLetters;
    @Setter
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
    public boolean validate(Account anAccount) throws PasswordNotValidException {
        Objects.requireNonNull(anAccount, Checks.argumentNotNull("anAccount", Account.class));
        LOGGER.debug("Try to validate account entity:");

        // Validate password in account:
        return this.validatePassword(anAccount.getPassword());
    }

    @Override
    public boolean validatePassword(String aPassword) throws PasswordNotValidException {
        Objects.requireNonNull(aPassword, Checks.argumentNotNull("aPassword", String.class));
        LOGGER.debug("Try to validate password;");

        // Check with rules:
        if (!this.passwordHasMinimumLength(aPassword))
            throw new PasswordNotValidException(ValidationRule.PASSWORD_MIN_LENGTH);

        if (this.passwordShouldUseNumbers) {
            if (!this.isContainNumbers(aPassword))
                throw new PasswordNotValidException(ValidationRule.PASSWORD_USE_NUMBERS);
        }

        if (this.passwordShouldUseUppercaseLetters) {
            if (!this.isContainUppercaseLetters(aPassword))
                throw new PasswordNotValidException(ValidationRule.PASSWORD_USE_UPPERCASE_LETTERS);
        }

        if (this.passwordShouldUseSpecialSymbols) {
            if (!this.isContainSpecialSymbols(aPassword))
                throw new PasswordNotValidException(ValidationRule.PASSWORD_USE_SPECIAL_SYMBOLS);
        }

        return true;
    }

    @Override
    public Map<String, Object> validationRules() {

        Map<String, Object> validationRules = new HashMap<>();
        validationRules.put("to.do.security.passwords.min-length", this.passwordMinLength);
        validationRules.put("to.do.security.passwords.use-numbers", this.passwordShouldUseNumbers);
        validationRules.put("to.do.security.passwords.use-uppercase-letter", this.passwordShouldUseUppercaseLetters);
        validationRules.put("to.do.security.passwords.use-special-symbols", this.passwordShouldUseSpecialSymbols);

        return validationRules;
    }

    /**
     * Method check if specified password string has minimum password length.
     * @param aPassword - password string.
     * @return - true if password is valid, in other cases - false.
     */
    public boolean passwordHasMinimumLength(String aPassword) {
        return aPassword.length() >= this.passwordMinLength;
    }

    /**
     * Method check if specified password string contains any numbers.
     * @param aPassword - password string.
     * @return - true if password is valid, in other cases - false.
     */
    public boolean isContainNumbers(String aPassword) {
        return aPassword.matches(".*\\d.*");
    }

    /**
     * Method check if specified password string contains letters in uppercase.
     * @param aPassword - password string.
     * @return - true if password is valid, in other cases - false.
     */
    public boolean isContainUppercaseLetters(String aPassword) {
        return aPassword.matches(".*[A-Z].*");
    }

    /**
     * Method check if specified password string contains special symbols.
     * @param aPassword - password string.
     * @return - true if password is valid, in other cases - false.
     */
    public boolean isContainSpecialSymbols(String aPassword) {
        Pattern p = Pattern.compile("[^a-z\\d ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(aPassword);
        return m.find();
    }
}
