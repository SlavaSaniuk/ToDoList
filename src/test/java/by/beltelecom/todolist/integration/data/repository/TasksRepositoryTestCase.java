package by.beltelecom.todolist.integration.data.repository;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TasksRepositoryTestCase {

    @Autowired
    private TasksRepository repository;

    private Task createTask() {
        return Task.newTask();
    }

    @Test
    public void save_newTask_shouldReturnCreatedTask() {
        Task task = repository.save(this.createTask());
        Assertions.assertNotEquals(0L, task.getId());
        Assertions.assertNotNull(task.getDateCreation());
    }

    @Test
    public void read_savedTask_shouldReturnSavedTask() {
        Task task = this.repository.save(this.createTask());
        Assertions.assertNotEquals(0L, task.getId());

        long expected_id = task.getId();
        Task actualTask = this.repository.findById(expected_id).get();

        Assertions.assertNotNull(actualTask);
        Assertions.assertNotNull(actualTask.getDateCreation());
        Assertions.assertEquals(expected_id, actualTask.getId());
    }
}
