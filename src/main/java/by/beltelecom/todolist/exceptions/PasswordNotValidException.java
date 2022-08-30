package by.beltelecom.todolist.exceptions;

import by.beltelecom.todolist.security.authentication.ValidationRule;

public class PasswordNotValidException extends Exception {
    private String msg;

    public PasswordNotValidException(String aMsg) {
        super(aMsg);
        this.msg = aMsg;
    }

    public PasswordNotValidException(ValidationRule validationRule) {
        this(String.format("Password is invalid: [%s] is invalid.", validationRule.name()));
    }
}
