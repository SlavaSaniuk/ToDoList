package by.beltelecom.todolist.security.authentication;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.exceptions.PasswordNotValidException;

import java.util.Map;

/**
 * {@link CredentialsValidator} security service bean used to validate accounts properties (e.g. mail, password, username).
 * Note: Implementation of {@link CredentialsValidator} should consider configuration properties
 * in application.properties with prefix "to.do.security";
 */
public interface CredentialsValidator {

    boolean validate(Account anAccount);

    /**
     * Method used to validate accounts password properties with rules defined in
     * {@link SecurityProperties#getPasswords()} via application.properties.
     * @param aPassword - accounts password string.
     * @return - true, if password is valid.
     */
    boolean validatePassword(String aPassword) throws PasswordNotValidException;

    Map<String, Object> validationRules();

}
