package by.beltelecom.todolist.utilities.printer;

import by.beltelecom.todolist.utilities.ArgumentChecker;

import java.util.List;

/**
 * Utilities class to work with {@link List} lists.
 */
public class ListUtils {

    /**
     * Create string from specified source list.
     * * @param aListOfObjects - source list.
     * @param formattedStringBuilder - formatted string builder for each element in list.
     * @return - string from specified list.
     */
    public static String listToString(List<Object> aListOfObjects, FormattedStringBuilder formattedStringBuilder) {
        // Check arguments:
        ArgumentChecker.nonNull(aListOfObjects, "aListOfObjects");
        ArgumentChecker.nonNull(formattedStringBuilder, "formattedStringBuilder");

        StringBuilder sb = new StringBuilder("List[");
        aListOfObjects.forEach(aObj -> sb.append(formattedStringBuilder.formattedString(aObj)).append(", "));
        sb.delete(sb.length()-2, sb.length());
        return sb.append("]").toString();
    }

}
