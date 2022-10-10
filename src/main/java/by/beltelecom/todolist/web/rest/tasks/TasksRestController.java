package by.beltelecom.todolist.web.rest.tasks;

import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tasks/", produces = "application/json")
public class TasksRestController {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksRestController.class);

    /**
     * Construct new {@link TasksRestController} REST HTTP controller bean.
     */
    public TasksRestController() {
        LOGGER.debug(SpringLogging.Creation.createBean(TasksRestController.class));
    }


}
