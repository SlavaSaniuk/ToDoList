package by.beltelecom.todolist.exceptions;

public class NotFoundException extends RuntimeException {

    private static final String MESSAGE = "Required entity of type[%s] not found in database.";

    public NotFoundException() {
        super(String.format(NotFoundException.MESSAGE, "EntityType"));
    }

    public NotFoundException(Class<?> aClass) {
        super(String.format(NotFoundException.MESSAGE, aClass.getCanonicalName()));
    }
}
