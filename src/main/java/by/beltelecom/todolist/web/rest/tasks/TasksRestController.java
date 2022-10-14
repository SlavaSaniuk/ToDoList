package by.beltelecom.todolist.web.rest.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import by.beltelecom.todolist.web.dto.rest.task.TasksListRestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * This controller bean handle http rest requests to get task objects.
 */
@RestController
@RequestMapping(value = "/rest/tasks/", produces = "application/json")
public class TasksRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksRestController.class); // Logger;
    // Spring beans:
    private final TasksService tasksService; // autowired in constructor;

    /**
     * Construct new {@link TasksRestController} http rest controller.
     * @param aTasksService - tasks service bean.
     */
    public TasksRestController(TasksService aTasksService) {
        // Check arguments:
        Objects.requireNonNull(aTasksService, Checks.argumentNotNull("aTasksService", TasksService.class));
        LOGGER.debug(SpringLogging.Creation.createBean(TasksRestController.class));

        // Mapping:
        this.tasksService = aTasksService;
    }

    /**
     * Get user tasks.
     * @param userId - user ID.
     * @return - List of user tasks.
     */
    @GetMapping("/{userId}")
    public TasksListRestDto listUserTasks(@PathVariable("userId") long userId) {
        LOGGER.debug("Try to get list of user[{}] tasks;", userId);
        List<Task> userTasks = this.tasksService.getUserTasksByUserId(userId);

        TasksListRestDto dto = new TasksListRestDto(userTasks);
        dto.setUserOwnerId(userId);
        return dto;
    }
}
