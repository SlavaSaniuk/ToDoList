package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.MultipleHandingException;
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

    /**
     * Update existed user task object with new task.
     * @param aModifiedTask - task with new values.
     * @param aUser - user owner.
     * @return - new modified task object.
     * @throws NotOwnerException - Throws in cases, when user tru to update not own task.
     * @throws NotFoundException - Throws in cases, when task not found in database.
     */
    @Transactional
    Task updateUserTask(Task aModifiedTask, User aUser) throws NotOwnerException, NotFoundException;

    /**
     * Create new user task.
     * @param aTask - task to be created.
     * @param aUser - user owner.
     * @return - Created user.
     */
    @Transactional
    Task createUserTask(Task aTask, User aUser);

    /**
     * Complete user task.
     * @param aTask - task to be completed.
     * @param aUser - task owner.
     * @throws NotOwnerException - Throws in cases when user try to complete not own task.
     * @throws NotFoundException - Throws in cases when task not exist in database.
     */
    @Transactional
    void completeUserTask(Task aTask, User aUser) throws NotOwnerException, NotFoundException;

    /**
     * Complete list of user tasks.
     * @param aTasksList - list fo tasks to be completed.
     * @param aUser - tasks owner.
     * @throws MultipleHandingException - Throws in cases when any task in list throw NOE, NFE exception
     * in {@link UserTasksManager#completeUserTask(Task, User)} method.
     */
    @Transactional
    void completeUserTasks(List<Task> aTasksList, User aUser) throws MultipleHandingException;

    /**
     * Delete user tasks.
     * @param aTasksList - tasks to be deleted.
     * @param aUser - tasks owner.
     * @throws MultipleHandingException - Throws in cases when any task in list throw NOE, NFE exception
     *      * in {@link UserTasksManager#deleteUserTask(Task, User)} method.
     */
    @Transactional
    void deleteUserTasks(List<Task> aTasksList, User aUser) throws MultipleHandingException;
}
