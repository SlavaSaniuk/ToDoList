package by.beltelecom.todolist.integration.data.repository;

import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
import by.beltelecom.todolist.configuration.services.TestsTaskService;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.TasksRepository;
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
public class TasksRepositoryTestCase {

    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private TestsTaskService testsTaskService;
    @Autowired
    private TestsUserService testsUserService;

    @Test
    void save_newTask_shouldSaveNewTask() {
        // Generate user and tasks:
        User user = this.testsUserService.testingUser("save1").getUser();
        Task task = this.testsTaskService.createTask(5);
        task.setOwner(user);
        Task saved = this.tasksRepository.save(task);
        Assertions.assertNotNull(saved);
    }

}
