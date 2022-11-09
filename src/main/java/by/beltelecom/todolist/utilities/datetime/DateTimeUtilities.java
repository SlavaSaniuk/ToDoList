package by.beltelecom.todolist.utilities.datetime;

import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.annotations.ExceptionSafe;
import by.beltelecom.todolist.utilities.annotations.RuntimeExceptionThrows;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utilities class to work with date and time.
 */
public class DateTimeUtilities {

    // Class variables:
    // Default JS Date string format:
    private static final DateTimeFormatter JS_DATE_STR_FORMATTER = DateTimeFormatter.ofPattern("yyyy, MM, dd");

    /**
     * Convert specified {@link LocalDate} date to JS date (format: yyyy, mm, dd);
     * @param aDate - date.
     * @return - JS Date string.
     */
    @ExceptionSafe
    public static String dateToJsString(LocalDate aDate) {
        if (aDate == null) return "";
        else return String.format("%d, %d, %d", aDate.getYear(), aDate.getMonthValue(), aDate.getDayOfMonth());
    }

    /**
     * Convert specified JS Date string to {@link LocalDate} date or throw {@link NullPointerException} exception
     * if JsDateStr is null ar {@link DateTimeParseException} exception if Js Date str in not parseable.
     * @param aJsDateStr - JS Date string.
     * @return - Date
     */
    @RuntimeExceptionThrows({NullPointerException.class, DateTimeParseException.class})
    public static LocalDate jsDateStrToDate(String aJsDateStr) {
        ArgumentChecker.nonNull(aJsDateStr, "aJsDateStr");
        return LocalDate.parse(aJsDateStr, DateTimeUtilities.JS_DATE_STR_FORMATTER);
    }

    /**
     * Exception safe implementation of some methods in {@link DateTimeUtilities} utilities class.
     */
    @ExceptionSafe
    public static class ExcSafe {

        /**
         * Exception safe {@link DateTimeUtilities#jsDateStrToDate(String)} method implementation.
         * If exception occurs method return null.
         */
        @ExceptionSafe
        public static LocalDate jsDateStrToDate(String aJsDateStr) {
            try {
                return DateTimeUtilities.jsDateStrToDate(aJsDateStr);
            }catch (NullPointerException | DateTimeParseException exc) {
                return null;
            }
        }
    }
}
