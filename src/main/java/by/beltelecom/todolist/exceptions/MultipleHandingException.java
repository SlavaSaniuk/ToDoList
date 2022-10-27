package by.beltelecom.todolist.exceptions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class MultipleHandingException extends Exception {

    // Class variables:
    @Getter
    private final Map<Object, Exception> objectExceptionMap = new HashMap<>();

    public MultipleHandingException() {
        super("Exception throws when handle multiple objects.");
    }
}
