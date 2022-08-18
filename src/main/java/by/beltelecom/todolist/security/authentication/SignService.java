package by.beltelecom.todolist.security.authentication;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.AccountAlreadyRegisteredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link SignService} security service bean used to register and authenticate users in application.
 */
@Service("SignService")
public interface SignService {

    /**
     * Register {@link Account} user account in database. Method create new {@link User} entity with name,
     * that specified in aUser argument.
     * @param anAccount - account to register;
     * @param aUser - account user;
     * @return - registered account;
     * @throws AccountAlreadyRegisteredException - throws in cases, when account with same email already registered.
     */
    @Transactional
    Account registerAccount(Account anAccount, User aUser) throws AccountAlreadyRegisteredException;

    /**
     * Loging specified {@link Account} account in application.
     * @param anAccount - account to login;
     * @return - account;
     * @throws BadCredentialsException - In case when user specified bad username or credentials.
     */
    Account loginAccount(Account anAccount) throws BadCredentialsException;
}
