package by.beltelecom.todolist.services.security;

import by.beltelecom.todolist.data.models.Account;
import org.springframework.transaction.annotation.Transactional;

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
     * @throws by.beltelecom.todolist.exceptions.RuntimeNotFoundException - when account is not found.
     */
    Account getAccountByEmail(String aEmail);

    /**
     * Save account entity in database.
     * @param anAccount - account entity to save;
     * @return - saved account entity (with id parameter);
     * @throws NullPointerException - if anAccount, User(in Account) argument is null.
     * @throws IllegalArgumentException if email, password or name properties of account is blank.
     */
    @Transactional
    Account saveAccount(Account anAccount);
}
