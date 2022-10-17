package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTasksManagerImpl implements UserTasksManager {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTasksManagerImpl.class);
    // Spring beans:

    /**
     * Construct new {@link UserTasksManagerImpl} service bean.
     */
    public UserTasksManagerImpl() {
        LOGGER.debug(SpringLogging.Creation.createBean(UserTasksManagerImpl.class));
    }

    @Override
    public void deleteUserTask(Task aTask, User aUser) throws NotOwnerException {
        LOGGER.debug(String.format("User[userId: %d] try to delete Task[taskId: %d];", aUser.getId(), aTask.getId()));

    }
}
