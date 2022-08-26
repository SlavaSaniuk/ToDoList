package by.beltelecom.todolist.web;

import lombok.Getter;

public enum ExceptionStatusCodes {

    BAD_CREDENTIALS_EXCEPTION (601),
    ACCOUNT_ALREADY_REGISTERED_EXCEPTION (602);

    @Getter
    private final int statusCode;

    ExceptionStatusCodes(int aStatusCode) {
        this.statusCode = aStatusCode;
    }
}
