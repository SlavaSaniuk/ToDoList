package by.beltelecom.todolist.web.dto.rest;

import by.beltelecom.todolist.web.ExceptionStatusCodes;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExceptionRestDto {

    // Class variables:
    protected boolean isException;

    protected String exceptionMessage;

    protected int exceptionCode;

    public ExceptionRestDto() {
        this.isException = false;
    }

    public ExceptionRestDto(String aExceptionMessage, int anExceptionCode) {
        this.isException = true;
        this.exceptionMessage = aExceptionMessage;
        this.exceptionCode = anExceptionCode;
    }

    public ExceptionRestDto(ExceptionStatusCodes aStatusCode) {
        this.isException = true;
        this.exceptionCode = aStatusCode.getStatusCode();
    }
}
