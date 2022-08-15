package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TasksServiceImpl implements TasksService{

    private final TasksRepository tasksRepository; // Autowired in ServicesConfiguration.class

    public TasksServiceImpl(TasksRepository aTaskRepository) {
        Objects.requireNonNull(aTaskRepository, "TaskRepository parameter constructor must be not null");
        this.tasksRepository = aTaskRepository;
    }

    @Override
    public Task getTaskById(long a_id) {
        if (a_id == 0L) throw new IllegalArgumentException("ID parameter must not be zero.");

        Optional<Task> taskOpt = this.tasksRepository.findById(a_id);
        if (taskOpt.isEmpty()) throw new NotFoundException(Task.class);

        return taskOpt.get();
    }

    @Override
    @Transactional
    public Task createTask(Task aTask) {
        Objects.requireNonNull(aTask, "Task mast be not null.");

        // Save task in db and assist it id parameter:
        return this.tasksRepository.save(aTask);
    }

    @Override
    public List<Task> getAllTasks() {
        return (List<Task>) this.tasksRepository.findAll();
    }

    @Override
    public void deleteById(long aId) {
        //Check at zero:
        if (aId == 0L) throw new IllegalArgumentException("Requested ID must be not zero.");

        this.tasksRepository.deleteById(aId);
    }
}
