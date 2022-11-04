package by.beltelecom.todolist.services.system;

import by.beltelecom.todolist.services.system.datetime.ServerDateTimeService;
import by.beltelecom.todolist.services.system.datetime.impl.ServerDateTimeServiceImpl;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemServicesConfiguration {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemServicesConfiguration.class);




    // =========================== BEANS DEFINITIONS ===========================

    @Bean
    public ServerDateTimeService serverDateTimeService() {
        LOGGER.debug(SpringLogging.Creation.createBean(ServerDateTimeService.class));
        return new ServerDateTimeServiceImpl();
    }
}
