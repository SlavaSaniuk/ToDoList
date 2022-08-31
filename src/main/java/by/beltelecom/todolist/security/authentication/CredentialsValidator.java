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

    /**
     * Method used to validate accounts entities properties with rules defined in
     * {@link SecurityProperties#getPasswords()} via application.properties.
     * @param anAccount - entity to validate.
     * @return - true, if account entity properties is valid.
     * @throws PasswordNotValidException - if accounts password property is not valid.
     */
    boolean validate(Account anAccount) throws PasswordNotValidException;

    /**
     * Method used to validate accounts password properties with rules defined in
     * {@link SecurityProperties#getPasswords()} via application.properties.
     * @param aPassword - accounts password string.
     * @return - true, if password is valid.
     * @throws PasswordNotValidException - if password is not valid.
     */
    boolean validatePassword(String aPassword) throws PasswordNotValidException;

    /**
     * Method return current validation rules.
     * @return - {@link Map} with rules and it's values.
     */
    Map<String, Object> validationRules();

}
