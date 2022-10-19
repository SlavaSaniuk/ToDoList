package by.beltelecom.todolist.services.security.owning;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Service bean is default implementation of {@link OwnerChecker} interface.
 * Used to check owners of {@link Task} tasks objects.
 */
public class TasksOwnerChecker implements OwnerChecker<Task> {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksOwnerChecker.class);
    // Spring beans
    private final TasksService tasksService; // Autowired in constructor;

    /**
     * Construct new {@link TasksOwnerChecker} service bean.
     * @param aTasksService - tasks service.
     */
    public TasksOwnerChecker(TasksService aTasksService) {
        Objects.requireNonNull(aTasksService, Checks.argumentNotNull("aTasksService", TasksService.class));
        LOGGER.debug(SpringLogging.Creation.createBean(TasksOwnerChecker.class));

        this.tasksService = aTasksService;
    }

    @Override
    public boolean isUserOwn(User aUser, Task aTask) {
        // Check arguments:
        Objects.requireNonNull(aUser, Checks.argumentNotNull("aUser", User.class));
        Objects.requireNonNull(aTask, Checks.argumentNotNull("aTask", Task.class));
        if (aUser.getId() == 0L) throw new IllegalArgumentException(Checks.Numbers.argNotZero("userId", Long.class));
        if (aTask.getId() == 0L) throw new IllegalArgumentException(Checks.Numbers.argNotZero("taskId", Long.class));
        LOGGER.debug(String.format("Check if user[userId: %d] own Task[taskId: %d];", aUser.getId(), aTask.getId()));

        // Get task from db:
        Task task = this.tasksService.getTaskById(aTask.getId());
        
        // Check owners:
        return task.getOwner().equals(aUser);
    }
}
