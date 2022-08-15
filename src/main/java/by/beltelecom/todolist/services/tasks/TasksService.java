package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interface for work with {@link Task} objects in application.
 */
@Service("TasksService")
public interface TasksService {

    Task getTaskById (long aId);

    /**
     * Create task object based on aTask parameter, save it in database, assign id attribute to it ard return it.
     * @param aTask - Base {@link Task} object.
     * @return - Task with id attribute.
     */
    @Transactional
    Task createTask(Task aTask);

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
    Task updateTask(Task aTask);
}
