package by.beltelecom.todolist.integration.services.security.owning;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.security.authentication.SignService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TasksOwnerCheckerTestsCase {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksOwnerCheckerTestsCase.class);
    // Spring beans:
    @Autowired
    private OwnerChecker<Task> tasksOwnerChecker;
    @Autowired
    private SignService signService;
    @Autowired
    private TasksService tasksService;

    @Test
    void isUserOwn_userIsOwnTasks_shouldReturnTrue() {
        // Create user:
        Account account = this.createAccount(1);
        Account created = this.signService.registerAccount(account, account.getUserOwner());
        User userOwner = created.getUserOwner();
        Assertions.assertNotNull(userOwner);
        LOGGER.debug(String.format("Created user[%s];", userOwner));

        // Create tasks:
        Task task1 = this.tasksService.createTask(this.getTestTask(), userOwner);
        LOGGER.debug(String.format("Created task[%s];", task1.toStringWithUser()));
        Assertions.assertNotNull(task1);

        Task task2 = this.tasksService.createTask(this.getTestTask(), userOwner);
        LOGGER.debug(String.format("Created task[%s];", task2.toStringWithUser()));
        Assertions.assertNotNull(task2);

        // Check own:
        Assertions.assertTrue(this.tasksOwnerChecker.isUserOwn(userOwner, task1));
        Assertions.assertTrue(this.tasksOwnerChecker.isUserOwn(userOwner, task2));
    }

    @Test
    void isUserOwn_userIsNotOwnTasks_shouldReturnFalse() {
        // Create users:
        Account account = this.createAccount(2);
        Account created = this.signService.registerAccount(account, account.getUserOwner());
        User userOwner = created.getUserOwner();
        Assertions.assertNotNull(userOwner);
        LOGGER.debug(String.format("Created user[%s];", userOwner));

        Account account2 = this.createAccount(3);
        Account created2 = this.signService.registerAccount(account2, account2.getUserOwner());
        User userOwner2 = created2.getUserOwner();
        Assertions.assertNotNull(userOwner2);
        LOGGER.debug(String.format("Created user[%s];", userOwner2));

        // Create tasks:
        Task task1 = this.tasksService.createTask(this.getTestTask(), userOwner);
        LOGGER.debug(String.format("Created task[%s];", task1.toStringWithUser()));
        Assertions.assertNotNull(task1);

        Task task2 = this.tasksService.createTask(this.getTestTask(), userOwner);
        LOGGER.debug(String.format("Created task[%s];", task2.toStringWithUser()));
        Assertions.assertNotNull(task2);

        // Check own:
        Assertions.assertFalse(this.tasksOwnerChecker.isUserOwn(userOwner2, task1));
        Assertions.assertFalse(this.tasksOwnerChecker.isUserOwn(userOwner2, task2));
    }

    private Account createAccount(int emailPrefix) {
        Account account = new Account();
        account.setEmail("test_email_" +emailPrefix +"@mail.com");
        account.setPassword("1!aAbBcCdD");

        User user = new User();
        user.setName("Test_name_"+emailPrefix);

        account.setUserOwner(user);
        LOGGER.debug("Created account: " +account);
        return account;
    }

    private Task getTestTask() {
        Task task = Task.newTask();
        task.setName(Randomizer.randomSentence(3));

        LOGGER.debug("Test task: " +task);
        return task;
    }
}
