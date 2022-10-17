package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;
import org.springframework.stereotype.Service;

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
    void deleteUserTask(Task aTask, User aUser) throws NotOwnerException;
}
