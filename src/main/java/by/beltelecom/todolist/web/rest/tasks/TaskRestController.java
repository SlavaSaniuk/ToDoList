package by.beltelecom.todolist.web.rest.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import by.beltelecom.todolist.web.dto.rest.TaskRestDto;
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

    @Autowired
    public TaskRestController(TasksService aTasksService) {
        // Check arguments:
        Objects.requireNonNull(aTasksService, Checks.argumentNotNull("aTasksService", TasksService.class));
        LOGGER.debug(SpringLogging.Creation.createBean(TaskRestController.class));

        // Map fields:
        this.tasksService = aTasksService;
    }

    @PostMapping(value = "/create-task", consumes = "application/json")
    public TaskRestDto createTask(@RequestBody TaskRestDto taskRestDto, @RequestAttribute("userObj") User userObj) {
        LOGGER.debug("Try to create task[{}] by user[{}] in TaskRestController#createTask;", taskRestDto, userObj);

        // Try to create task:
        Task task = this.tasksService.createTask(taskRestDto.toEntity(), userObj);
        return new TaskRestDto(task);
    }


}
