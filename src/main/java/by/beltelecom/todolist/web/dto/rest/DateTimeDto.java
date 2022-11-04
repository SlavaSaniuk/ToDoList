package by.beltelecom.todolist.web.dto.rest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class DateTimeDto {

    // Class variables:
    private String serverDateStr;

    public static DateTimeDto ofDate(LocalDate aDate) {
        String jsDateStr = String.format("%d, %d, %d", aDate.getYear(), aDate.getMonthValue(), aDate.getDayOfMonth());

        DateTimeDto dto = new DateTimeDto();
        dto.setServerDateStr(jsDateStr);
        return dto;
    }
}
