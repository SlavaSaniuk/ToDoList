package by.beltelecom.todolist.integration.services.system.datetime;

import by.beltelecom.todolist.services.system.SystemServicesConfiguration;
import by.beltelecom.todolist.services.system.datetime.ServerDateTimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@Import(SystemServicesConfiguration.class)
public class ServerDateTimeServiceTestsCase {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerDateTimeServiceTestsCase.class);
    // Spring beans:
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private ServerDateTimeService serverDateTimeService;

    @Test
    void getServerDate_serverDate_shouldReturnLocalDate() {
        LocalDate now = this.serverDateTimeService.getServerDate();

        Assertions.assertNotNull(now);
        LOGGER.debug(String.format("Server date: %s;", now));
    }
}
