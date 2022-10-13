package by.beltelecom.todolist.web.rest.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/task/", produces = "application/json")
public class TaskRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRestController.class); // Logger;
    // Spring beans:


    public void createTask() {

    }


}
