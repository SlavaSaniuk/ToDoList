package by.beltelecom.todolist.web.controllers.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

/**
 * HTTP controller bean handle requests to get user's tasks at "/tasks" url.
 */
@Controller
@RequestMapping("/tasks")
public class TasksController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);
    private final TasksService tasksService; // Service bean (Autowired in constructor);

    /**
     * Construct new instance of {@link TasksController} http controller bean.
     * @param aTasksService - {@link TasksService} service bean;
     */
    public TasksController(TasksService aTasksService) {
        // Check arguments:
        Objects.requireNonNull(aTasksService, Checks.argumentNotNull("aTasksService", TasksService.class));

        LOGGER.debug(SpringLogging.Creation.createBean(TasksController.class));

        // Map properties:
        this.tasksService = aTasksService;
    }


    @GetMapping("/{id}")
    public ModelAndView getUserTasksPage(@PathVariable("id") long userId) {
        LOGGER.debug("Handle http request at '/tasks/{}' url;", userId);
        ModelAndView mav = new ModelAndView("tasks");

        // Get user tasks:
        User user = new User();
        user.setId(userId);
        List<Task> userTasks = this.tasksService.getUserTasks(user);

        // Add to view:
        mav.addObject("tasks", userTasks);

        return mav;
    }
}
