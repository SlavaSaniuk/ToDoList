package by.beltelecom.todolist.integration.services.task;

import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
import by.beltelecom.todolist.configuration.services.TestsTaskService;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.services.tasks.UserTasksManager;
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

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ServicesTestsConfiguration.class)
public class UserTasksManagerTestsCase {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTasksManagerTestsCase.class);
    // Spring beans:
    @Autowired
    private UserTasksManager userTasksManager;
    @Autowired
    private TestsUserService testsUserService;
    @Autowired
    private TestsTaskService testsTaskService;

    @Autowired
    private TasksService tasksService;

    @Test
    void deleteUserTask_userTaskIsExist_shouldDeleteUserTask() {
        // Create nd save user:
        User user = this.testsUserService.testingUser("1").getUser();

        // Create and save task:
        Task task = TaskWrapper.Creator.createTask(Randomizer.randomSentence(4));
        task = this.tasksService.createTask(task, user);

        // Get task by id:
        Task founded = this.tasksService.getTaskById(task.getId());
        Assertions.assertEquals(task, founded);

        // Delete task:
        try {
            this.userTasksManager.deleteUserTask(task, user);

            long taskId = task.getId();
            // Get task by id:
            Assertions.assertThrows(NotFoundException.class, () -> this.tasksService.findTaskById(taskId));

        }catch (NotOwnerException | NotFoundException exc) {
            Assertions.fail();
        }


    }

    @Test
    void deleteUserTask_userTaskNotFound_shouldThrowNFE() {
        User user = this.testsUserService.testingUser("2").getUser();

        Task task = TaskWrapper.Creator.createTask();
        task.setId(5678L);

        Assertions.assertThrows(NotFoundException.class, () -> this.userTasksManager.deleteUserTask(task, user));
    }

    @Test
    void deleteUserTask_userNotOwnTask_shouldThrowNOE() {
        // Generate user and tasks:
        User user1 = this.testsUserService.testingUser("5").getUser();
        User user2 = this.testsUserService.testingUser("6").getUser();

        Task task = this.testsTaskService.testTask(user2);

        Assertions.assertThrows(NotOwnerException.class, ()-> this.userTasksManager.deleteUserTask(task, user1));
    }

    @Test
    void loadUserAllTasks_userHasNotAnyTasks_shouldReturnEmptyList() {
        User user = this.testsUserService.testingUser("loadUserTask1").getUser();
        List<Task> userTasks = this.userTasksManager.loadUserAllTasks(user);

        Assertions.assertNotNull(userTasks);
        Assertions.assertTrue(userTasks.isEmpty());
    }

    @Test
    void loadUserAllTasks_userHas3Tasks_shouldReturnListOfTasks() {
        User user = this.testsUserService.testingUser("loadUserTask2").getUser();
        this.testsTaskService.testTasks(user, 3);

        List<Task> userTasks = this.userTasksManager.loadUserAllTasks(user);

        Assertions.assertNotNull(userTasks);
        Assertions.assertFalse(userTasks.isEmpty());
        Assertions.assertEquals(3, userTasks.size());

        LOGGER.debug("Loaded tasks:");
        userTasks.forEach((task) -> LOGGER.debug(TaskWrapper.wrap(task).printer().toStringWithUser()));
    }
}
