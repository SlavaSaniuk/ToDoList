package by.beltelecom.todolist.services.security.owning;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OwningConfiguration {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(OwningConfiguration.class);
    // Spring beans:
    private TasksService tasksService; // Autowired via setter;

    /**
     * Create new configuration spring bean.
     */
    public OwningConfiguration() {
        LOGGER.debug(SpringLogging.Creation.createBean(OwningConfiguration.class));
    }

    // ============================= BEANS DEFINITIONS ===============================
    @Bean("TasksOwnerChecker")
    public OwnerChecker<Task> tasksOwnerChecker() {
        return new TasksOwnerChecker(this.tasksService);
    }

    // ============================= AUTOWIRING ======================================

    @Autowired
    public void setTasksService(TasksService aTasksService) {
        LOGGER.debug(SpringLogging.Autowiring.autowireInConfiguration(TasksService.class, OwningConfiguration.class));
        this.tasksService = aTasksService;
    }
}
