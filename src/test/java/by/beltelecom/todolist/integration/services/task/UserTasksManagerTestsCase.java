package by.beltelecom.todolist.integration.services.task;

import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
import by.beltelecom.todolist.configuration.services.TestsTaskService;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.converter.TaskStatus;
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

    @Test
    void modifyUserTask_userModifiedOwnTask_shouldReturnTaskWithNewValues() {
        // Generate user and task:
        User user = this.testsUserService.testingUser("modifyUserTask1").getUser();
        Task task = this.testsTaskService.testTask(user);

        // Modify task:
        String modifiedTaskName = "modified name";
        String modifiedDescription = "modified desc";
        task.setName(modifiedTaskName);
        task.setDescription(modifiedDescription);

        try {
            Task modifiedTask = this.userTasksManager.updateUserTask(task, user);

            Assertions.assertEquals(task, modifiedTask);
            Assertions.assertEquals(modifiedTaskName, modifiedTask.getName());
            Assertions.assertEquals(modifiedDescription, modifiedTask.getDescription());

            LOGGER.debug(String.format("Modified task[%s];", modifiedTask));
        } catch (NotOwnerException | NotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void modifyUserTask_taskIsNotExist_shouldThrowNFE() {
        // Generate user and task:
        User user = this.testsUserService.testingUser("modifyUserTask2").getUser();
        Task task = TaskWrapper.Creator.createTask();

        Assertions.assertThrows(NotFoundException.class, () -> this.userTasksManager.updateUserTask(task, user));
    }

    @Test
    void modifyUserTask_userNotOwnTask_shouldThrowNOE() {
        // Generate users and task:
        User user = this.testsUserService.testingUser("modifyUserTask3").getUser();
        User user2 = this.testsUserService.testingUser("modifyUserTask33").getUser();
        Task task = this.testsTaskService.testTask(user);

        // Modify task:
        String modifiedTaskName = "modified name";
        String modifiedDescription = "modified desc";
        task.setName(modifiedTaskName);
        task.setDescription(modifiedDescription);

        Assertions.assertThrows(NotOwnerException.class, () -> this.userTasksManager.updateUserTask(task, user2));
    }

    @Test
    void createUserTask_newTask_shouldCreateNewTask() {
        // Generate user and task:
        User user = this.testsUserService.testingUser("createUserTask1").getUser();
        Task task = this.testsTaskService.createTask(6);

        Task created = this.userTasksManager.createUserTask(task, user);
        Assertions.assertNotNull(created);
        Assertions.assertNotEquals(0L, created.getId());

        // Get created task from DB:
        try {
            Task founded = this.tasksService.findTaskById(created);
            Assertions.assertNotNull(founded);
            Assertions.assertEquals(created, founded);

            LOGGER.debug(String.format("Created task: %s;", created));
        } catch (NotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void completeUserTask_userHasOwnTask_shouldCompleteTask() {
        // Generate user and task:
        User user = this.testsUserService.testingUser("CompleteUserTask1").getUser();
        Task task = this.testsTaskService.testTask(user);
        Assertions.assertEquals(TaskStatus.WORKING, task.getTaskStatus());

        // Complete user task
        try {
            this.userTasksManager.completeUserTask(task, user);

            // Get task by id:
            Task completed = this.tasksService.findTaskById(task);

            Assertions.assertNotNull(completed);
            Assertions.assertEquals(task, completed);
            Assertions.assertEquals(TaskStatus.COMPLETED, completed.getTaskStatus());

        } catch (NotOwnerException | NotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void completeUserTask_userNotOwnTask_shouldThrowNOE() {
        // Generate users and task:
        User user = this.testsUserService.testingUser("CompleteUserTask2").getUser();
        User user2 = this.testsUserService.testingUser("CompleteUserTask3").getUser();
        Task task = this.testsTaskService.testTask(user);
        Assertions.assertEquals(TaskStatus.WORKING, task.getTaskStatus());

        Assertions.assertThrows(NotOwnerException.class, ()-> this.userTasksManager.completeUserTask(task, user2));
    }

    @Test
    void completeUserTask_userHasNotTask_shouldThrowNFE() {
        // Generate users and task:
        User user = this.testsUserService.testingUser("CompleteUserTask4").getUser();
        Task task = this.testsTaskService.createTask(5);
        Assertions.assertEquals(TaskStatus.WORKING, task.getTaskStatus());

        Assertions.assertThrows(NotFoundException.class, ()-> this.userTasksManager.completeUserTask(task, user));
    }

}
