package by.beltelecom.todolist.web;

import lombok.Getter;

public enum ExceptionStatusCodes {

    BAD_CREDENTIALS_EXCEPTION (601);

    @Getter
    private final int statusCode;

    ExceptionStatusCodes(int aStatusCode) {
        this.statusCode = aStatusCode;
    }
}
