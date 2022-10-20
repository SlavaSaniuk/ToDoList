package by.beltelecom.todolist.exceptions;

public class RuntimeNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Required entity of type[%s] not found in database.";

    public RuntimeNotFoundException() {
        super(String.format(RuntimeNotFoundException.MESSAGE, "EntityType"));
    }

    public RuntimeNotFoundException(Class<?> aClass) {
        super(String.format(RuntimeNotFoundException.MESSAGE, aClass.getCanonicalName()));
    }
}
