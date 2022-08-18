package by.beltelecom.todolist.services.security;

import by.beltelecom.todolist.data.models.Account;

import java.util.Optional;

/**
 * {@link  AccountsService} service bean user to manipulate with account object's in database.
 */
public interface AccountsService {

    /**
     * Find account entity in database be email.
     * @param aEmail - email parameter;
     * @return Optional of account or empty optional;
     * @throws NullPointerException - when email argument is null.
     * @throws IllegalArgumentException - when email argument is blank.
     */
    Optional<Account> getAccountOptByEmail(String aEmail);

    /**
     * Find account entity in database be email.
     * @param aEmail - email parameter;
     * @return Account entity;
     * @throws NullPointerException - when email argument is null.
     * @throws IllegalArgumentException - when email argument is blank.
     * @throws by.beltelecom.todolist.exceptions.NotFoundException - when account is not found.
     */
    Account getAccountByEmail(String aEmail);
}
