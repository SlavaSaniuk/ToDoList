package by.beltelecom.todolist.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * JSON string converter from {@link String} to {@link LocalDate} types.
 * If you need to change default date format then change {@link LocalDateConverter#DATE_FORMAT_PATTERN} field.
 */
@Component
public class LocalDateConverter implements Converter<String, LocalDate> {

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
    }
}
