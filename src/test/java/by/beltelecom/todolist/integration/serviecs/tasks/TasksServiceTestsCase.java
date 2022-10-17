package by.beltelecom.todolist.integration.serviecs.tasks;

import by.beltelecom.todolist.configuration.AuthenticationTestsConfiguration;
import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
import by.beltelecom.todolist.configuration.bean.TestsUsersService;
import by.beltelecom.todolist.configuration.services.TestsTasksService;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.tasks.TasksService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
@Import({AuthenticationTestsConfiguration.class, ServicesTestsConfiguration.class})
public class TasksServiceTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksServiceTestsCase.class);
    // Spring beans:
    @Autowired
    private TasksService tasksService;
    @Autowired
    private TestsUsersService testsUsersService;
    @Autowired
    private TestsTasksService testsTasksService;

    @BeforeEach
    void beforeEach() {
        this.testsUsersService.registerUser();
    }

    @AfterEach
    void afterEach() {
        this.testsUsersService.deleteUser(this.testsUsersService.getTestUser());
    }

    @Test
    void getUserTasksByUserId_userHasTasks_shouldReturnListOfTasks() {
        // Create users tasks:
        Task task1 = Task.newTask();
        task1.setName("Test task 1.");
        Task task2 = Task.newTask();
        task2.setName("Test task 2.");
        this.tasksService.createTask(task1, this.testsUsersService.getTestUser().getUser());
        this.tasksService.createTask(task2, this.testsUsersService.getTestUser().getUser());

        // Get users tasks:
        long userId = this.testsUsersService.getTestUser().getUser().getId();
        List<Task> usersTasks = this.tasksService.getUserTasksByUserId(userId);

        Assertions.assertNotNull(usersTasks);
        Assertions.assertFalse(usersTasks.isEmpty());
        Assertions.assertEquals(2, usersTasks.size());

        // Print users tasks:
        usersTasks.forEach((task) -> LOGGER.debug("Task[{}];", task));

    }

    @Test
    void deleteTaskById_taskExist_shouldDeleteTask() {
        // Generate task:
        Task generated = this.testsTasksService.createTask(this.testsUsersService.getTestUser().getUser());
        Assertions.assertNotNull(generated);
        LOGGER.debug("Generated task: " +generated);

        long taskId = generated.getId();
        Assertions.assertNotEquals(0L, taskId);

        Task founded = this.tasksService.getTaskById(taskId);
        Assertions.assertNotNull(founded);
        LOGGER.debug("Founded task: " +founded);

        // Delete task:
        this.tasksService.deleteById(taskId);

        Assertions.assertThrows(NotFoundException.class, () -> {this.tasksService.getTaskById(taskId);});

    }

}
