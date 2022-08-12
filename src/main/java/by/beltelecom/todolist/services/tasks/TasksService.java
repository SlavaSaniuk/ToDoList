package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TasksService")
public interface TasksService {

    Task getTaskById (long aId);

    /**
     * Create task object based on aTask parameter, save it in database, assign id attribute to it ard return it.
     * @param aTask - Base {@link Task} object.
     * @return - Task with id attribute.
     */
    Task createTask(Task aTask);

    /**
     * Return all tasks from database without filters.
     * @return - List of all tasks.
     */
    List<Task> getAllTasks();

}
