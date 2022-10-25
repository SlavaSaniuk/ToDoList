package by.beltelecom.todolist.integration.services.security.owning;

import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
import by.beltelecom.todolist.configuration.models.TestingUser;
import by.beltelecom.todolist.configuration.services.TestsTaskService;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;
import by.beltelecom.todolist.services.security.owning.OwnerChecker;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.utilities.Randomizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ServicesTestsConfiguration.class)
public class TasksOwnerCheckerTestsCase {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksOwnerCheckerTestsCase.class);
    // Spring beans:
    @Autowired
    private OwnerChecker<Task> tasksOwnerChecker;
    @Autowired
    private TasksService tasksService;
    @Autowired
    private TestsUserService testsUserService;
    @Autowired
    private TestsTaskService testsTaskService;

    @Test
    void isUserOwn_userIsOwnTasks_shouldReturnTrue() {
        // Create and register user:
        TestingUser testingUser = this.testsUserService.testingUser("1");
        User userOwner = testingUser.getUser();
        Assertions.assertNotNull(userOwner);
        LOGGER.debug(String.format("Created user[%s];", userOwner));

        // Create tasks:
        Task task1 = this.tasksService.createTask(this.getTestTask(), userOwner);
        LOGGER.debug(String.format("Created task[%s];", TaskWrapper.wrap(task1).printer().toStringWithUser()));
        Assertions.assertNotNull(task1);

        Task task2 = this.tasksService.createTask(this.getTestTask(), userOwner);
        LOGGER.debug(String.format("Created task[%s];", TaskWrapper.wrap(task2).printer().toStringWithUser()));
        Assertions.assertNotNull(task2);

        // Check own:
        Assertions.assertDoesNotThrow(() -> this.tasksOwnerChecker.isUserOwn(userOwner, task1));
        Assertions.assertDoesNotThrow(() -> this.tasksOwnerChecker.isUserOwn(userOwner, task2));
    }

    @Test
    void isUserOwn_userIsNotOwnTasks_shouldThrowNOE() {
        // Create users:
        TestingUser testingUser2 = this.testsUserService.testingUser("2");
        TestingUser testingUser3 = this.testsUserService.testingUser("3");
        User userOwner = testingUser2.getUser();
        Assertions.assertNotNull(userOwner);
        LOGGER.debug(String.format("Created user[%s];", userOwner));

        User userOwner2 = testingUser3.getUser();
        Assertions.assertNotNull(userOwner2);
        LOGGER.debug(String.format("Created user[%s];", userOwner2));

        // Create tasks:
        Task task1 = this.tasksService.createTask(this.getTestTask(), userOwner);
        LOGGER.debug(String.format("Created task[%s];", TaskWrapper.wrap(task1).printer().toStringWithUser()));
        Assertions.assertNotNull(task1);

        Task task2 = this.tasksService.createTask(this.getTestTask(), userOwner);
        LOGGER.debug(String.format("Created task[%s];", TaskWrapper.wrap(task2).printer().toStringWithUser()));
        Assertions.assertNotNull(task2);

        // Check own:
        Assertions.assertThrows(NotOwnerException.class ,() -> this.tasksOwnerChecker.isUserOwn(userOwner2, task1));
        Assertions.assertThrows(NotOwnerException.class ,() -> this.tasksOwnerChecker.isUserOwn(userOwner2, task2));
    }

    @Test
    void isUserOwn_taskIsNotExist_shouldThrowNFE() {
        // Generate user and task:
        User user = this.testsUserService.testingUser("isUserOwn4").getUser();
        Task task = this.testsTaskService.createTask();

        Assertions.assertThrows(NotFoundException.class, () -> this.tasksOwnerChecker.isUserOwn(user, task));
    }


    private Task getTestTask() {
        Task task = TaskWrapper.createTask();
        task.setName(Randomizer.randomSentence(3));

        LOGGER.debug("Test task: " +task);
        return task;
    }
}
