package by.beltelecom.todolist.integration.services.task;

import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
import by.beltelecom.todolist.configuration.services.TestsTaskService;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.converter.TaskStatus;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.RuntimeNotFoundException;
import by.beltelecom.todolist.services.tasks.TasksService;
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
@Import({ServicesTestsConfiguration.class})
public class TasksServiceTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksServiceTestsCase.class);
    // Spring beans:
    @Autowired
    private TasksService tasksService;
    @Autowired
    private TestsTaskService testsTaskService;
    @Autowired
    private TestsUserService testsUserService;

    @Test
    void getUserTasksByUserId_userHasTasks_shouldReturnListOfTasks() {
        // Create user:
        User user = this.testsUserService.testingUser("getUserTasksByUserId1").getUser();
        // Create users tasks:
        Task task1 = TaskWrapper.Creator.createTask();
        task1.setName("Test task 1.");
        Task task2 = TaskWrapper.Creator.createTask();
        task2.setName("Test task 2.");
        this.tasksService.createTask(task1, user);
        this.tasksService.createTask(task2, user);

        // Get users tasks:
        long userId = user.getId();
        List<Task> usersTasks = this.tasksService.getUserTasksByUserId(userId);

        Assertions.assertNotNull(usersTasks);
        Assertions.assertFalse(usersTasks.isEmpty());
        Assertions.assertEquals(2, usersTasks.size());

        // Print users tasks:
        usersTasks.forEach((task) -> LOGGER.debug("Task[{}];", task));

    }

    @Test
    void deleteTaskById_taskExist_shouldDeleteTask() {
        // Generate user task:
        User user = this.testsUserService.testingUser("deleteTaskById1").getUser();
        Task generated = this.testsTaskService.testTask(user);
        Assertions.assertNotNull(generated);
        LOGGER.debug("Generated task: " +generated);

        long taskId = generated.getId();
        Assertions.assertNotEquals(0L, taskId);

        Task founded = this.tasksService.getTaskById(taskId);
        Assertions.assertNotNull(founded);
        LOGGER.debug("Founded task: " +founded);

        // Delete task:
        this.tasksService.deleteById(taskId);

        Assertions.assertThrows(RuntimeNotFoundException.class, () -> this.tasksService.getTaskById(taskId));

    }

    @Test
    void modifyTask_taskIsExist_shouldModifyTask() {
        String modifiedName = "MODIFIED NAME";
        String modifiedDesc = "MODIFIED DESCRIPTION";

        // Generate user and task:
        User user = this.testsUserService.testingUser("modifyTask1").getUser();
        Task task = this.testsTaskService.testTask(user);
        Assertions.assertNotNull(task);
        long taskId = task.getId();
        Assertions.assertNotEquals(0L, taskId);

        // Modify task:
        Task toModifyTask = TaskWrapper.Creator.createTask(taskId);
        toModifyTask.setName(modifiedName);
        toModifyTask.setDescription(modifiedDesc);

        // Update task:
        try {
            Task modifiedTask = this.tasksService.modifyTask(toModifyTask);
            Assertions.assertNotNull(modifiedTask);
            Assertions.assertEquals(taskId, modifiedTask.getId());
            Assertions.assertEquals(modifiedName, modifiedTask.getName());
            Assertions.assertEquals(modifiedDesc, modifiedTask.getDescription());

            LOGGER.debug(String.format("Modified task[%s];", TaskWrapper.wrap(modifiedTask).printer().toStringWithUser()));
        } catch (NotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void modifyTask_taskIsNotExist_shouldThrowNFE() {
        // Create task:
        Task task = TaskWrapper.Creator.createTask(888888L);

        Assertions.assertThrows(NotFoundException.class, () -> this.tasksService.modifyTask(task));
    }

    @Test
    void isTaskExist_taskIsNotExist_shouldReturnFalse() {
        Task task = TaskWrapper.Creator.createTask();
        Assertions.assertFalse(this.tasksService.isTaskExist(task.getId()));
    }

    @Test
    void isTaskExist_taskIsExist_shouldReturnTrue() {
        // Generate user and task:
        User user = this.testsUserService.testingUser("isTaskExist1").getUser();
        Task task = this.testsTaskService.testTask(user);

        Assertions.assertTrue(this.tasksService.isTaskExist(task.getId()));
    }

    @Test
    void isTaskExist_taskIsNotExist_shouldThrowNFE() {
        Task task = TaskWrapper.Creator.createTask();
        Assertions.assertThrows(NotFoundException.class, ()->this.tasksService.isTaskExist(task));
    }

    @Test
    void updateStatus_taskIsExist_shouldUpdateTaskStatus() {
        // Create user and task:
        User user = this.testsUserService.testingUser("updateStatus1").getUser();
        Task task = this.testsTaskService.testTask(user);

        // Update task status:
        TaskStatus status = TaskStatus.COMPLETED;
        try {
            Task updated = this.tasksService.updateStatus(task, status);

            Assertions.assertNotNull(updated);
            Assertions.assertEquals(task, updated);
            Assertions.assertEquals(status, updated.getTaskStatus());

            LOGGER.debug(String.format("Task with updates status: [%s];", updated));

        } catch (NotFoundException e) {
            Assertions.fail();
        }

    }

    @Test
    void updateStatus_taskNotExist_shouldThrowNFE() {
        Assertions.assertThrows(NotFoundException.class, ()-> this.tasksService.updateStatus(TaskWrapper.Creator.createTask(), TaskStatus.COMPLETED));
    }

}
