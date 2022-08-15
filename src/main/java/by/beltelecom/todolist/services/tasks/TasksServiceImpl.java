package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Default service implementation of {@link TasksService} bean.
 */
public class TasksServiceImpl implements TasksService{

    private final TasksRepository tasksRepository; // Autowired in ServicesConfiguration.class
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksServiceImpl.class);

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
    @Transactional
    public void deleteById(long aId) {
        //Check at zero:
        if (aId == 0L) throw new IllegalArgumentException("Requested ID must be not zero.");

        this.tasksRepository.deleteById(aId);
    }

    @Override
    public Task updateTask(Task aTask) {
        LOGGER.debug("Try to update entity object{} in db: ", aTask);
        // Check id:
        if (aTask.getId() == 0L) throw new IllegalArgumentException("Parameter aTask should have a nonzero id value");

        // Check if already exist in db:
        if(!this.tasksRepository.existsById(aTask.getId())) throw new NotFoundException(Task.class);

        return this.tasksRepository.save(aTask);
    }


}
