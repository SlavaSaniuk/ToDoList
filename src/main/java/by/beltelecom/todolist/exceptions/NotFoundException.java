package by.beltelecom.todolist.exceptions;

/**
 * Exception throws in cases when any want get entity from database but entity not exist in db.
 */
public class NotFoundException extends Exception {

    /**
     * Construct new {@link NotFoundException} exception.
     * @param aObj - not founded entity.
     */
    public NotFoundException(Object aObj) {
        super(String.format("Required entity[%s] of type[%s] is not found;", aObj, aObj.getClass()));
    }

}
