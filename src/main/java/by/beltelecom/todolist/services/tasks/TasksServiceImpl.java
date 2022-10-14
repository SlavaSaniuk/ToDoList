package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Default service implementation of {@link TasksService} bean.
 */
@Transactional
public class TasksServiceImpl implements TasksService{

    private final TasksRepository tasksRepository; // Autowired in ServicesConfiguration.class;
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksServiceImpl.class); // Logger;

    /**
     * Construct new {@link TasksServiceImpl} service bean;
     * @param aTaskRepository - {@link TasksRepository} repository bean.
     */
    public TasksServiceImpl(TasksRepository aTaskRepository) {
        LOGGER.debug(SpringLogging.Creation.createBean(TasksServiceImpl.class));

        // Check arguments:
        Objects.requireNonNull(aTaskRepository, Checks.argumentNotNull("aTaskRepository", TasksRepository.class));

        // maps arguments:
        this.tasksRepository = aTaskRepository;
    }

    @Override
    public Task getTaskById(long a_id) {
        if (a_id == 0L) throw new IllegalArgumentException("ID parameter must not be zero.");

        Optional<Task> taskOpt = this.tasksRepository.findById(a_id);
        if (taskOpt.isEmpty()) throw new NotFoundException(Task.class);

        return taskOpt.get();
    }

    /**
     * Create task object based on aTask parameter, save it in database, assign id attribute to it ard return it.
     * @param aTask - Base {@link Task} object.
     * @return - Task with id attribute.
     */
    @Transactional
    public Task createTask(Task aTask) {

        // Check parameters:
        if (aTask.getName().isEmpty()) // check if name is empty;
            throw new IllegalArgumentException(Checks.Strings.stringNotEmpty("name"));

        LOGGER.debug("Save new task[{}] in database:", aTask);

        // Save task in db and assist it id parameter:
        return this.tasksRepository.save(aTask);
    }

    @Override
    @Transactional
    public Task createTask(Task aTask, User aUser) {
        // Check parameters:
        Objects.requireNonNull(aTask, Checks.argumentNotNull("aTask", Task.class));
        Objects.requireNonNull(aUser, // Check owner;
                Checks.propertyOfArgumentNotNull("userOwner", "aTask", Task.class));

        LOGGER.debug("Save new task[{}] of user[{}] in database:", aTask, aUser);

        // Map properties:
        aTask.setOwner(aUser);

        // Create task:
        aTask = this.createTask(aTask);

        return aTask;
    }

    @Override
    public List<Task> getAllTasks() {
        return this.tasksRepository.findAll();
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

    @Override
    public List<Task> getUserTasks(User aUser) {
        // Check arguments:
        Objects.requireNonNull(aUser, Checks.argumentNotNull("aUser", User.class));
        if (aUser.getId() == 0L) throw new IllegalArgumentException(Checks.Numbers.argNotZero("id", Long.class));

        LOGGER.debug("Try to get user[{}] tasks;", aUser);

        // Get tasks:
        List<Task> tasks = this.tasksRepository.findAllByOwner(aUser);
        aUser.setTasks(tasks);

        return tasks;
    }

    @Override
    public List<Task> getUserTasksByUserId(long aUserId) {
        LOGGER.debug("Try to get user[{}] tasks by user ID;", aUserId);

        User user = new User();
        user.setId(aUserId);
        return this.getUserTasks(user);
    }


}
