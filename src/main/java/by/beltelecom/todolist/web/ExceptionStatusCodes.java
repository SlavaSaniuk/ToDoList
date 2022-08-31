package by.beltelecom.todolist.web;

import lombok.Getter;

/**
 * {@link ExceptionStatusCodes} enum contains constants of HTTP WEB EXCEPTION STATUS CODES.
 */
public enum ExceptionStatusCodes {

    /**
     * ExceptionStatusCodes#BAD_CREDENTIALS_EXCEPTION 601 code describe exception, when account properties is incorrect.
     * @see by.beltelecom.todolist.security.authentication.SignService#loginAccount;
     * @see by.beltelecom.todolist.web.rest.sign.SignRestController#logInAccount;
     */
    BAD_CREDENTIALS_EXCEPTION (601),

    /**
     * ExceptionStatusCodes#ACCOUNT_ALREADY_REGIstERED_ExCEPTION 602 code describe exception, when account already registered;
     * @see by.beltelecom.todolist.security.authentication.SignService#registerAccount;
     * @see by.beltelecom.todolist.web.rest.sign.SignRestController#registerAccount;
     */
    ACCOUNT_ALREADY_REGISTERED_EXCEPTION (602),

    /**
     * ExceptionStatusCodes#PASSWORD_NOT_VALID_EXCEPTION 603 code describe exception, when accounts password property is invalid;
     * @see by.beltelecom.todolist.web.rest.sign.SignRestController#registerAccount;
     */
    PASSWORD_NOT_VALID_EXCEPTION (603);

    @Getter
    private final int statusCode;

    /**
     * Construct custom HTTP WEB EXCEPTION STATUS CODE.
     * @param aStatusCode - code.
     */
    ExceptionStatusCodes(int aStatusCode) {
        this.statusCode = aStatusCode;
    }
}
