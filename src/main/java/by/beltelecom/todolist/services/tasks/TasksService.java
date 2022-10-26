package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.enums.TaskStatus;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface for work with {@link Task} objects in application.
 */
@Service("TasksService")
public interface TasksService {

    Task getTaskById (long aId);

    /**
     * Find task entity in database by ID.
     * @param aId - task ID;
     * @return - founded task object.
     * @throws NotFoundException - throws in cases, when task with required id not exist in database.
     */
    Task findTaskById(long aId) throws NotFoundException;

    /**
     * Find task entity in database by  specified task.id.
     * @param aTask - task object with id.
     * @return - founded task object.
     * @throws NotFoundException - throws in cases, when task with required id not exist in database.
     */
    Task findTaskById(Task aTask) throws NotFoundException;

    /**
     * Create new {@link Task} entity object in database;
     * @param aTask - task to create;
     * @param aUser - user which create task;
     * @return created task.
     */
    Task createTask(Task aTask, User aUser);

    /**
     * Return all tasks from database without filters.
     * @return - List of all tasks.
     */
    List<Task> getAllTasks();

    /**
     * Delete task object by it's ID.
     * @param aId - task ID to delete.
     */
    void deleteById(long aId);

    /**
     * Update entity object with new properties from {@param aTask} parameter.
     * Note! aTask entity should have an id property initialized.
     * @param aTask - entity object to update (with new properties);
     * @return - updated entity object.
     */
    Task updateTask(Task aTask);

    /**
     * Update task object with new fields values of aTask param.
     * @param aTask - task with new fields values.
     * @return - Modified task.
     * @throws NotFoundException - Throws in cases, when task not found in database.
     */
    Task modifyTask(Task aTask) throws NotFoundException;

    List<Task> getUserTasks(User aUser);

    /**
     * Get users tasks by user ID.
     * @param aUserId - user ID.
     * @return - list of user tasks.
     */
    List<Task> getUserTasksByUserId(long aUserId);

    /**
     * Check if task is saved in database. Exception save!
     * @param aTaskId - task id.
     * @return - true, if task is saved in db.
     */
    boolean isTaskExist(long aTaskId);

    /**
     * Check if task is saved in database. If task is not exist, method throw NFE exception.
     * @param aTask - task.
     * @throws NotFoundException - if task is not exist.
     */
    void isTaskExist(Task aTask) throws NotFoundException;

    /**
     * Update task status property.
     * @param aTask - task to be updated.
     * @param aTaskStatus - status to set.
     * @return - Task with updated status.
     * @throws NotFoundException - Throws in cases when task not exist in database.
     */
    Task updateStatus(Task aTask, TaskStatus aTaskStatus) throws NotFoundException;
}
