package by.beltelecom.todolist.web.rest.tasks;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.web.dto.rest.TaskRestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/task/", produces = "application/json")
public class TaskRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRestController.class); // Logger;
    // Spring beans:


    @PostMapping(value = "/create-task", consumes = "application/json")
    public TaskRestDto createTask(@RequestBody TaskRestDto taskRestDto, @RequestAttribute("userObj") User userObj) {
        LOGGER.debug("Try to create task[{}] by user[{}] in TaskRestController#createTask;", taskRestDto, userObj);

        return taskRestDto;
    }


}
