package by.beltelecom.todolist.web.rest.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.services.tasks.UserTasksManager;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import by.beltelecom.todolist.web.ExceptionStatusCodes;
import by.beltelecom.todolist.web.dto.rest.ExceptionRestDto;
import by.beltelecom.todolist.web.dto.rest.task.TaskRestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/rest/task/", produces = "application/json")
public class TaskRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRestController.class); // Logger;
    // Spring beans:
    private final TasksService tasksService; //Autowired in constructor;
    private final UserTasksManager userTasksManager; //Autowired in constructor;

    /**
     * Construct new {@link TaskRestController} HTTP REST controller.
     * @param aTasksService - tasks service bean.
     */
    @Autowired
    public TaskRestController(TasksService aTasksService, UserTasksManager aUserTaskManager) {
        // Check arguments:
        Objects.requireNonNull(aTasksService, Checks.argumentNotNull("aTasksService", TasksService.class));
        ArgumentChecker.nonNull(aUserTaskManager, "aUserTaskManager");
        LOGGER.debug(SpringLogging.Creation.createBean(TaskRestController.class));

        // Map fields:
        this.tasksService = aTasksService;
        this.userTasksManager = aUserTaskManager;
    }

    /**
     * Create new {@link Task} object. Method handle HTTP REST request on "/rest/task/create-task"
     * url to create new user task.
     * @param taskRestDto - Task DTO to create.
     * @param userObj - Task owner (Initialized in {@link by.beltelecom.todolist.security.rest.filters.JsonWebTokenFilter}) via request attribute.
     * @return - Create task object with initialized ID.
     */
    @PostMapping(value = "/create-task", consumes = "application/json")
    public TaskRestDto createTask(@RequestBody TaskRestDto taskRestDto, @RequestAttribute("userObj") User userObj) {
        LOGGER.debug("Try to create task[{}] by user[{}] in TaskRestController#createTask;", taskRestDto, userObj);

        // Try to create task:
        Task task = this.tasksService.createTask(taskRestDto.toEntity(), userObj);
        return new TaskRestDto(task);
    }

    @GetMapping(value = "/delete-task", consumes = "application/json")
    public ExceptionRestDto deleteUserTask(@RequestParam("id") long aTaskId, @RequestAttribute("userObj") User userObj) {
        // Create task by id:
        Task task = TaskWrapper.Creator.createTask(aTaskId);
        LOGGER.debug(String.format("Try to delete Task[%s] of user[%s];", task, userObj));

        // Try to delete task:
        try {
            this.userTasksManager.deleteUserTask(task, userObj);
        } catch (NotOwnerException e) {
            LOGGER.warn(e.getMessage());
            return new ExceptionRestDto(e.getMessage(), ExceptionStatusCodes.NOT_OWNER_EXCEPTION.getStatusCode());
        } catch (NotFoundException e) {
            LOGGER.warn(e.getMessage());
            return new ExceptionRestDto(e.getMessage(), ExceptionStatusCodes.NOT_FOUND_EXCEPTION.getStatusCode());
        }

        // In no exception throws, return empty exception DTO.
        return ExceptionRestDto.noExceptionDto();
    }

}
