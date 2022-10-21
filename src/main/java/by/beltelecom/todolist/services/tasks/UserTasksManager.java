package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service bean that manage user tasks.
 */
@Service("UserTasksManager")
public interface UserTasksManager {

    /**
     * Delete user task.
     * @param aTask - task to delete.
     * @param aUser - owner of task.
     * @throws NotOwnerException - throws, if user try to delete not his object.
     */
    @Transactional
    void deleteUserTask(Task aTask, User aUser) throws NotOwnerException, NotFoundException;

    /**
     * Load all user tasks.
     * If user hasn't any tasks, method return empty list (not null).
     * @param aUser - user.
     * @return - List of users tasks.
     */
    List<Task> loadUserAllTasks(User aUser);
}
