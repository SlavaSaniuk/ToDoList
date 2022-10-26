package by.beltelecom.todolist.integration.data.repository;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.data.repositories.UsersRepository;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UsersRepositoryTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersRepositoryTestsCase.class);
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TasksRepository tasksRepository;

    @Test
    void getUserByIdWithTask_noTasks_shouldReturnEmptyList() {
        User toSave = new User();
        toSave.setName("TestName");
        toSave = this.usersRepository.save(toSave);

        User founded = this.usersRepository.findUserByIdWithTasks(toSave.getId());

        Assertions.assertNotNull(founded);
        Assertions.assertTrue(founded.getTasks().isEmpty());
    }

    @Test
    void getUserByIdWithTask_someTasks_shouldReturnListOfTasks() {
        User toSave = new User();
        toSave.setName("TestName");
        toSave = this.usersRepository.save(toSave);
        Assertions.assertNotNull(toSave);
        Assertions.assertNotEquals(0L, toSave.getId());
        long userId = toSave.getId();

        Task task1 = TaskWrapper.Creator.createTask();
        task1.setName("testName");
        task1.setOwner(toSave);

        Task task2 = TaskWrapper.Creator.createTask();
        task2.setName("testName");
        task2.setOwner(toSave);

        task1 = this.tasksRepository.save(task1);
        task2 = this.tasksRepository.save(task2);

        toSave.getTasks().add(task1);
        toSave.getTasks().add(task2);
        this.usersRepository.save(toSave);

        List<Task> foundedTasks = this.tasksRepository.findAllByOwner(toSave);
        Assertions.assertEquals(2, foundedTasks.size());
        foundedTasks.forEach(task -> LOGGER.info("Task: " +task));


        User founded = this.usersRepository.findUserByIdWithTasks(userId);
        LOGGER.info("Founded user: " +founded.toString());

        Assertions.assertNotNull(founded.getTasks());
        Assertions.assertFalse(founded.getTasks().isEmpty());
    }
}
