package by.beltelecom.todolist.services.system.datetime;

import java.time.LocalDate;

/**
 * Service work with system date/time.
 * Used to get system date/time, set system time zone.
 */
public interface ServerDateTimeService {

    /**
     * Get server date.
     * @return - server date.
     */
    LocalDate getServerDate();
}
