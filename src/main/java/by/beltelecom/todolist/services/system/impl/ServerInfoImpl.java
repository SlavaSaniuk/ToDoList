package by.beltelecom.todolist.services.system.impl;

import by.beltelecom.todolist.services.system.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class ServerInfoImpl implements ServerInfo {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInfoImpl.class);

    @Override
    public LocalDate getServerDate() {
        LOGGER.debug("Getting server date;");
        LocalDate serverDate = LocalDate.now();
        LOGGER.debug(String.format("Server date: %s;", serverDate));
        return serverDate;
    }
}
