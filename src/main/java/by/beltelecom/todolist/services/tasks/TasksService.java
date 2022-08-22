package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface for work with {@link Task} objects in application.
 */
@Service("TasksService")
@Transactional
public interface TasksService {

    Task getTaskById (long aId);

    /**
     * Create new {@link Task} entity object in database;
     * @param aTask - task to create;
     * @param aUser - user which create task;
     * @return created task.
     */
    @Transactional
    Task createTask(Task aTask, User aUser);

    /**
     * Return all tasks from database without filters.
     * @return - List of all tasks.
     */
    List<Task> getAllTasks();

    @Transactional
    void deleteById(long aId);

    /**
     * Update entity object with new properties from {@param aTask} parameter.
     * Note! aTask entity should have an id property initialized.
     * @param aTask - entity object to update (with new properties);
     * @return - updated entity object.
     */
    @Transactional
    Task updateTask(Task aTask);

    List<Task> getUserTasks(User aUser);
}
