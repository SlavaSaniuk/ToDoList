package by.beltelecom.todolist.utilities.datetime;

import java.time.LocalDate;

/**
 * Utilities class to work with date and time.
 */
public class DateTimeUtilities {

    /**
     * Convert specified {@link LocalDate} date to JS date (format: yyyy, mm, dd);
     * @param aDate - date.
     * @return - JS Date string.
     */
    public static String dateToJsString(LocalDate aDate) {
        if (aDate == null) return "";
        else return String.format("%d, %d, %d", aDate.getYear(), aDate.getMonthValue(), aDate.getDayOfMonth());
    }
}
