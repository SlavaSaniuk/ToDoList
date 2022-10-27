package by.beltelecom.todolist.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception used in cases when any exception occurs during handing multiple objects.
 * Put objects that throw exception and exception instance in {@link MultipleHandingException#getObjectExceptionMap()}
 * map (!!! by default it's empty, not null) to access exceptions in caller methods.
 */
public class MultipleHandingException extends Exception {

    // Class variables:
    @Getter
    private final Map<Object, Exception> objectExceptionMap = new HashMap<>();

    /**
     * Construct new exception instance with exception message.
     * @param aMsg - exception message.
     */
    public MultipleHandingException(String aMsg) {
        super(aMsg);
    }
}
