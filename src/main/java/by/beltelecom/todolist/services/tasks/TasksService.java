package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import org.springframework.stereotype.Service;

@Service("TasksService")
public interface TasksService {

    Task getTaskById (long aId);

    Task createTask(Task aTask);


}
