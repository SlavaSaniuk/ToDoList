package by.beltelecom.todolist.web.dto.rest;

import by.beltelecom.todolist.utilities.datetime.DateTimeUtilities;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO object to work with dates and time.
 * Convert {@link LocalDate} date in JS String format date.
 */
@Getter @Setter
public class DateTimeDto {

    // Class variables:
    private String dateStr; // JS date string;

    /**
     * Construct new DateTimeDto of specified {@link LocalDate} date.
     * @param aDate - date.
     * @return - new DateTimeDto.
     */
    public static DateTimeDto ofDate(LocalDate aDate) {
        DateTimeDto dto = new DateTimeDto();
        dto.setDateStr(DateTimeUtilities.dateToJsString(aDate));
        return dto;
    }
}
