package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.data.wrappers.UserWrapper;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;
import by.beltelecom.todolist.services.security.owning.OwnerChecker;
import by.beltelecom.todolist.services.security.owning.TasksOwnerChecker;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of {@link UserTasksManager} service bean.
 */
public class UserTasksManagerImpl implements UserTasksManager {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTasksManagerImpl.class);
    // Spring beans:
    private final TasksService tasksService; // Autowired in constructor;
    private final OwnerChecker<Task> tasksOwnerChecker; // Autowired in constructor;

    /**
     * Construct new {@link UserTasksManagerImpl} service bean.
     * @param aTasksService - tasks service bean.
     * @param aTasksOwnerChecker - tasks owner checker service bean.
     */
    public UserTasksManagerImpl(TasksService aTasksService, OwnerChecker<Task> aTasksOwnerChecker) {
        Objects.requireNonNull(aTasksService, Checks.argumentNotNull("aTasksService", TasksService.class));
        Objects.requireNonNull(aTasksOwnerChecker, Checks.argumentNotNull("aTasksOwnerChecker", TasksOwnerChecker.class));
        LOGGER.debug(SpringLogging.Creation.createBean(UserTasksManagerImpl.class));

        this.tasksService = aTasksService;
        this.tasksOwnerChecker = aTasksOwnerChecker;
    }

    @Override
    @Transactional
    public void deleteUserTask(Task aTask, User aUser) throws NotOwnerException, NotFoundException {
        // Check arguments:
        ArgumentChecker.nonNull(aTask, "aTask");
        ArgumentChecker.nonNull(aUser, "aUser");
        ArgumentChecker.idNotZero(TaskWrapper.wrap(aTask));
        ArgumentChecker.idNotZero(UserWrapper.wrap(aUser));
        LOGGER.debug(String.format("User[userId: %d] try to delete task [%s];", aUser.getId(), TaskWrapper.wrap(aTask).printer().toStringWithUser()));

        // Get task by id:
        Task task = this.tasksService.findTaskById(aTask);

        // Check owners:
        this.tasksOwnerChecker.isUserOwn(aUser, task);

        // Try to delete task:
        this.tasksService.deleteById(task.getId());
        LOGGER.debug(String.format("Task [%s] - deleted;", TaskWrapper.wrap(task).printer().toStringWithUser()));
    }

    @Override
    public List<Task> loadUserAllTasks(User aUser) {
        LOGGER.debug(String.format("Load user[%s] all tasks;", aUser));
        return new ArrayList<>(this.tasksService.getUserTasks(aUser));
    }

    @Override
    @Transactional
    public Task updateUserTask(Task aModifiedTask, User aUser) throws NotOwnerException, NotFoundException {
        // Check if user own task:
        this.tasksOwnerChecker.isUserOwn(aUser, aModifiedTask);

        // Map user to task:
        aModifiedTask.setOwner(aUser);

        // Update user task:
        return this.tasksService.modifyTask(aModifiedTask);
    }

    /**
     * Create user task. Method check arguments and call {@link TasksService#createTask(Task, User)} method.
     * @param aTask - task to be created.
     * @param aUser - user owner.
     * @return - Created task.
     */
    @Override
    public Task createUserTask(Task aTask, User aUser) {
        // Check arguments:
        ArgumentChecker.nonNull(aTask, "aTask");
        ArgumentChecker.nonNull(aUser, "aUser");
        LOGGER.debug(String.format("User[%s] try to create new Task[taskName: %s]", aUser, aTask.getName()));

        // Create task:
        return this.tasksService.createTask(aTask, aUser);
    }
}
