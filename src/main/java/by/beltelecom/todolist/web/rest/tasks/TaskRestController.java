package by.beltelecom.todolist.web.rest.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.data.wrappers.UserWrapper;
import by.beltelecom.todolist.exceptions.MultipleHandingException;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;
import by.beltelecom.todolist.services.tasks.UserTasksManager;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import by.beltelecom.todolist.web.ExceptionStatusCodes;
import by.beltelecom.todolist.web.dto.rest.ExceptionRestDto;
import by.beltelecom.todolist.web.dto.rest.task.TaskRestDto;
import by.beltelecom.todolist.web.dto.rest.task.TasksListRestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller bean handle HTTP requests on "rest/task/*" urls to da any octions on user task object.
 * UserObj requester attribute initialized in {@link by.beltelecom.todolist.security.rest.filters.JsonWebTokenFilter}
 * filter by JWT.
 */
@RestController
@RequestMapping(value = "/rest/task/", produces = "application/json")
public class TaskRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRestController.class); // Logger;
    // Spring beans:
    private final UserTasksManager userTasksManager; //Autowired in constructor;

    /**
     * Construct new {@link TaskRestController} HTTP REST controller.
     * @param aUserTaskManager - service bean.
     */
    @Autowired
    public TaskRestController(UserTasksManager aUserTaskManager) {
        // Check arguments:
        ArgumentChecker.nonNull(aUserTaskManager, "aUserTaskManager");
        LOGGER.debug(SpringLogging.Creation.createBean(TaskRestController.class));

        // Map fields:
        this.userTasksManager = aUserTaskManager;
    }

    /**
     * Create new {@link Task} object. Method handle POST HTTP request on "/rest/task/create-task"
     * url to create new user task.
     * @param taskRestDto - Task DTO to create.
     * @param userObj - Task owner (Initialized in {@link by.beltelecom.todolist.security.rest.filters.JsonWebTokenFilter}) via request attribute.
     * @return - Create task object with initialized ID.
     */
    @PostMapping(value = "/create-task", consumes = "application/json")
    public TaskRestDto createTask(@RequestBody TaskRestDto taskRestDto, @RequestAttribute("userObj") User userObj) {
        LOGGER.debug("Try to create task[{}] by user[{}] in TaskRestController#createTask;", taskRestDto, userObj);

        // Try to create task:
        Task task = this.userTasksManager.createUserTask(taskRestDto.toEntity(), userObj);
        return new TaskRestDto(task);
    }

    /**
     * Delete user task. Method handle GET HTTP request on "/rest/task/delete-task?id={TASK_ID}" url to delete user task.
     * @param aTaskId - task id of task to be deleted.
     * @param userObj - user (initialized in JWT filter).
     * @return - Exception DTO with no exception flag, or with initialized flag if exception occurs.
     */
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

    /**
     * Load user tasks.
     * @param userId - user ID.
     * @return - List of user tasks.
     */
    @GetMapping("/{userId}")
    public TasksListRestDto loadUserTasks(@PathVariable("userId") long userId, @RequestAttribute("userObj") User userObj) {
        // Check if user try to load own tasks or any users tasks:
        if (userObj.getId() == userId)
            LOGGER.debug(String.format("User[%s] try to load list of own tasks;", UserWrapper.wrap(userObj).printer().printUserOnlyId()));
        else LOGGER.debug(String.format("User[%s] try to load list of User[%s] tasks;",
                UserWrapper.wrap(userObj).printer().printUserOnlyId(), UserWrapper.creator().ofId(userId).printer().printUserOnlyId()));

        List<Task> userTasks = this.userTasksManager.loadUserAllTasks(UserWrapper.creator().ofId(userId).getWrappedUser());
        TasksListRestDto dto = new TasksListRestDto(userTasks);
        dto.setUserOwnerId(userId);
        return dto;
    }

    /**
     * Update user task. Method handle POST HTTP request to update user task.
     * @param taskRestDto - DTO with new task fields values.
     * @param userObj - user (initialized in JWT filter).
     * @return - Modified task object as DTO.
     */
    @PostMapping(value = "/update-task", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskRestDto updateUserTask(@RequestBody TaskRestDto taskRestDto, @RequestAttribute("userObj") User userObj) {
        // Check arguments:
        ArgumentChecker.nonNull(taskRestDto, "taskRestDto");
        Task task = taskRestDto.toEntity();

        // Update task:
        LOGGER.debug(String.format("User[%s] try to update task[taskId: %d] with new values [%s];", userObj, taskRestDto.getTaskId(), taskRestDto));
        try {
            Task updatedTask = this.userTasksManager.updateUserTask(task, userObj);
            return TaskRestDto.of(updatedTask);
        } catch (NotOwnerException e) {
            LOGGER.warn(e.getMessage());
            return new TaskRestDto(new ExceptionRestDto(e.getMessage(), ExceptionStatusCodes.NOT_OWNER_EXCEPTION.getStatusCode()));
        } catch (NotFoundException e) {
            LOGGER.warn(e.getMessage());
            return new TaskRestDto(new ExceptionRestDto(e.getMessage(), ExceptionStatusCodes.NOT_FOUND_EXCEPTION.getStatusCode()));
        }
    }

    /**
     * Complete user task. Method handle GET HTTP request on "/rest/task/complete-task?id={TASK_ID}" url to complete user task.
     * @param aTaskId - task ID to be completed.
     * @param userObj - user (initialized in JWT filter).
     * @return - Exception DTO with no exception flag, or with initialized flag if exception occurs.
     */
    @GetMapping(value = "/complete-task", consumes = "application/json")
    public ExceptionRestDto completeUserTask(@RequestParam("id") long aTaskId, @RequestAttribute("userObj") User userObj) {
        // Check arguments:
        ArgumentChecker.nonNull(userObj, "userObj");
        LOGGER.debug(String.format("User[%s] try to complete task with task id[taskId: %d]", userObj, aTaskId));

        // Complete user task:
        try {
            this.userTasksManager.completeUserTask(new TaskWrapper.Builder().ofId(aTaskId).build(), userObj);
            return ExceptionRestDto.noExceptionDto();
        } catch (NotOwnerException e) {
            LOGGER.warn(e.getMessage());
            return new ExceptionRestDto(e.getMessage(),  ExceptionStatusCodes.NOT_OWNER_EXCEPTION.getStatusCode());
        } catch (NotFoundException e) {
            LOGGER.warn(e.getMessage());
            return new ExceptionRestDto(e.getMessage(), ExceptionStatusCodes.NOT_FOUND_EXCEPTION.getStatusCode());
        }
    }

    /**
     * Complete user tasks. Method handle POST HTTP request on "/rest/task/complete-tasks" url to complete user tasks.
     * @param tasksListRestDto - DTO with list of task to be completed.
     * @param userObj - user what initiate request (initialized in JWT filter).
     * @return - Response entity with OK status and {@link TasksListRestDto#getTasksList()} list will contain
     * all completed tasks if all tasks completed, or with BAD_REQUEST (400) status if any exception occurs
     * and {@link TasksListRestDto#getTasksList()} list will contain all uncompleted tasks.
     */
    @PostMapping(value = "/complete-tasks", consumes = "application/json")
    public ResponseEntity<TasksListRestDto> completeUserTasks(@RequestBody TasksListRestDto tasksListRestDto, @RequestAttribute("userObj") User userObj) {
        ArgumentChecker.nonNull(tasksListRestDto, "taskListRestDto");
        ArgumentChecker.nonNull(userObj, "userObj");

        // Get list of tasks:
        List<Task> tasksList = new ArrayList<>();
        tasksListRestDto.getTasksList().forEach((dto) -> tasksList.add(dto.toEntity()));

        // Complete tasks:
        try {
            this.userTasksManager.completeUserTasks(tasksList, userObj);
            // If task completed:
            return new ResponseEntity<>(new TasksListRestDto(tasksList), HttpStatus.OK);
        } catch (MultipleHandingException e) {
            // If not all tasks completed:
            List<TaskRestDto> notCompletedTask = new ArrayList<>();
            e.getObjectExceptionMap().forEach((task, exc) -> notCompletedTask.add(TaskRestDto.of((Task) task)));

            TasksListRestDto exceptionDto = new TasksListRestDto(e.getMessage(), 400);
            exceptionDto.setTasksList(notCompletedTask);

            return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
        }

    }

}
