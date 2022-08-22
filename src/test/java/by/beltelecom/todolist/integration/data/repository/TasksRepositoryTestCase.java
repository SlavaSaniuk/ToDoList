package by.beltelecom.todolist.integration.data.repository;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.data.repositories.UsersRepository;
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
    @Autowired
    private UsersRepository usersRepository;

    /*
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

     */

    @Test
    void findAllByUserOwner_noTasks_shouldReturnEmptyList() {
        User owner = new User();
        owner.setId(2);

        Assertions.assertEquals(0, this.repository.findAllByOwner(owner).size());
    }

    @Test
    void findAllByUserOwner_2tasks_shouldReturnListWith2tasks() {
        User owner = new User();
        owner.setName("TestName");
        owner = usersRepository.save(owner);

        Task task1 = Task.newTask();
        task1.setName("anyName");
        task1.setOwner(owner);
        Task task2 = Task.newTask();
        task2.setName("anyName");
        task2.setOwner(owner);

        this.repository.save(task1);
        this.repository.save(task2);

        Assertions.assertEquals(2, this.repository.findAllByOwner(owner).size());

    }
}
