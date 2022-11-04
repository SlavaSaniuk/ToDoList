package by.beltelecom.todolist.services.system.datetime.impl;

import by.beltelecom.todolist.services.system.datetime.ServerDateTimeService;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * Implementation of {@link ServerDateTimeService} service bean.
 */
public class ServerDateTimeServiceImpl implements ServerDateTimeService {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerDateTimeServiceImpl.class);

    /**
     * Construct new {@link ServerDateTimeServiceImpl} service bean.
     */
    public ServerDateTimeServiceImpl() {
        LOGGER.debug(SpringLogging.Creation.createBean(ServerDateTimeServiceImpl.class));
    }

    /**
     * Get server date.
     * @return - server date.
     */
    @Override
    public LocalDate getServerDate() {
        LOGGER.debug(String.format("Get server date: %s;", LocalDate.now()));
        return LocalDate.now();
    }
}
