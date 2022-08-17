package by.beltelecom.todolist.exceptions;

import by.beltelecom.todolist.data.models.Account;

public class AccountAlreadyRegisteredException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "[Account[%s]] already registered.";

    public AccountAlreadyRegisteredException(Account anAccount) {
        super(String.format(DEFAULT_MESSAGE, anAccount.toString()));
    }
}
