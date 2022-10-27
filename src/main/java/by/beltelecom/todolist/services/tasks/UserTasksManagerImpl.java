package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.enums.TaskStatus;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.data.wrappers.UserWrapper;
import by.beltelecom.todolist.exceptions.MultipleHandingException;
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

        // Get task by id:
        Task task = this.tasksService.findTaskById(aTask);

        // Check owners:
        this.tasksOwnerChecker.isUserOwn(aUser, task);

        // Try to delete task:
        this.tasksService.deleteById(task.getId());
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
    @Transactional
    public Task createUserTask(Task aTask, User aUser) {
        // Check arguments:
        ArgumentChecker.nonNull(aTask, "aTask");
        ArgumentChecker.nonNull(aUser, "aUser");
        LOGGER.debug(String.format("User[%s] try to create new Task[taskName: %s]", aUser, aTask.getName()));

        // Create task:
        return this.tasksService.createTask(aTask, aUser);
    }

    /**
     * Method implements {@link UserTasksManager#completeUserTask(Task, User)}.
     * Method set {@link TaskStatus#COMPLETED} status
     * to specified user task object.
     * @param aTask - task to be completed.
     * @param aUser - task owner.
     * @throws NotOwnerException - Throws in cases when user try to complete not osn task.
     * @throws NotFoundException - Throws in cases when task not exist in database.
     */
    @Override
    @Transactional
    public void completeUserTask(Task aTask, User aUser) throws NotOwnerException, NotFoundException {
        // Check arguments:
        ArgumentChecker.nonNull(aTask, "aTask");
        ArgumentChecker.nonNull(aUser, "aUser");

        // Check user owner:
        this.tasksOwnerChecker.isUserOwn(aUser, aTask);

        // Complete user task:
        LOGGER.debug(String.format("User[%s] try to complete task[%s];", aUser, aTask));
        this.tasksService.updateStatus(aTask, TaskStatus.COMPLETED);
    }

    /**
     * Implements {@link UserTasksManager#completeUserTasks(List, User)}}.
     * Method iterate specified list and for each task call {@link UserTasksManager#completeUserTask(Task, User)} method.
     * @param aTasksList - list fo tasks to be completed.
     * @param aUser - tasks owner.
     * @throws MultipleHandingException - Throws in cases when {@link UserTasksManager#completeUserTask(Task, User)}
     * method throws NOE, NFE for any task in list.
     */
    @Override
    @Transactional
    public void completeUserTasks(List<Task> aTasksList, User aUser) throws MultipleHandingException {
        // Check arguments:
        ArgumentChecker.nonNull(aTasksList, "aTask list");
        ArgumentChecker.nonNull(aUser, "aUser");
        LOGGER.debug(String.format("User[%s] try to complete list of task[%s];", aUser, TaskWrapper.printer().listOfTaskId(aTasksList)));

        // Create instance of exception:
        MultipleHandingException exception =
                new MultipleHandingException(String.format("Exception occurs when complete tasks: %s;", TaskWrapper.printer().listOfTaskId(aTasksList)));

        // Complete user tasks:
        aTasksList.forEach((task -> {
            try {
                this.completeUserTask(task, aUser);
            } catch (NotOwnerException | NotFoundException e) {
                exception.getObjectExceptionMap().put(task, e);
            }
        }));

        // if any exceptions occurs throw MHE:
        if (!exception.getObjectExceptionMap().isEmpty()) throw exception;
    }
}
