package by.beltelecom.todolist.integration.data.wrapper;

import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
import by.beltelecom.todolist.configuration.services.TestsTaskService;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ServicesTestsConfiguration.class)
public class TaskWrapperTestsCase {

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskWrapperTestsCase.class);
    // Spring beans:
    @Autowired
    private TestsTaskService testsTaskService;
    @Autowired
    private TestsUserService testsUserService;

    @Test
    void listOfTaskId_fourTask_shouldReturnStringOfTaskId() {
        // Generate tasks:
        User user = this.testsUserService.testingUser("listOfTaskId1").getUser();
        List<Task> tasksList = this.testsTaskService.testTasks(user, 4);

        // Get printer string:
        String result = TaskWrapper.printer().listOfTaskId(tasksList);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());

        LOGGER.debug(String.format("Result: %s;", result));
    }

}
