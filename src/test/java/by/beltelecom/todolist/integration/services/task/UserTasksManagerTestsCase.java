package by.beltelecom.todolist.integration.services.task;

import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ServicesTestsConfiguration.class)
public class UserTasksManagerTestsCase {

    // Spring beans:
    @Autowired
    private UserTasksManager userTasksManager;
    @Autowired
    private TestsUserService testsUserService;

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
}
