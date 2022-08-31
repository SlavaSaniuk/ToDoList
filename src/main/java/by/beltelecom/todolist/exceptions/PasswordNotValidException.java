package by.beltelecom.todolist.exceptions;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.security.authentication.ValidationRule;

/**
 * {@link PasswordNotValidException} exception throws in cases, when accounts password property is not valid.
 * @see by.beltelecom.todolist.security.authentication.CredentialsValidator#validatePassword(String);
 * @see by.beltelecom.todolist.security.authentication.CredentialsValidator#validate(Account)
 */
public class PasswordNotValidException extends Exception {

    /**
     * Construct new {@link PasswordNotValidException} exception object.
     * @param aMsg - exception message.
     */
    public PasswordNotValidException(String aMsg) {
        super(aMsg);
    }

    /**
     * Construct new {@link PasswordNotValidException} exception object with exception message in format:
     * "Password is invalid: [VALIDATION_RULE] is invalid".
     * @param validationRule - {@link ValidationRule} rule.
     */
    public PasswordNotValidException(ValidationRule validationRule) {
        this(String.format("Password is invalid: [%s] is invalid.", validationRule.name()));
    }
}
