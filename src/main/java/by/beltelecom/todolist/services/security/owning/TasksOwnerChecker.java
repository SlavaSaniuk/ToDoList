package by.beltelecom.todolist.services.security.owning;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;
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
    public void isUserOwn(User aUser, Task aTask) throws NotOwnerException, NotFoundException {
        // Check arguments:
        Objects.requireNonNull(aUser, Checks.argumentNotNull("aUser", User.class));
        Objects.requireNonNull(aTask, Checks.argumentNotNull("aTask", Task.class));
        LOGGER.debug(String.format("Check if user[userId: %d] own Task[taskId: %d];", aUser.getId(), aTask.getId()));

        // Get task from db:
        if (aTask.getOwner() == null) aTask = this.tasksService.findTaskById(aTask.getId());

        // Check owners:
        if (!aTask.getOwner().equals(aUser)) throw new NotOwnerException(aUser, TaskWrapper.Creator.createTask(aTask.getId()));
    }
}
