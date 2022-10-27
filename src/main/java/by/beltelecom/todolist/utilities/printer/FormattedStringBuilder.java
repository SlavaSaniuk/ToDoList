package by.beltelecom.todolist.utilities.printer;

/**
 * {@link FormattedStringBuilder} interface define method that allow convert any object to string.
 */
public interface FormattedStringBuilder {

    /**
     * Convert source object to string.
     * @param aObj - source object.
     * @return - string.
     */
    String formattedString(Object aObj);
}
