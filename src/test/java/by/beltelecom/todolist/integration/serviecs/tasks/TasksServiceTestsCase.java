package by.beltelecom.todolist.integration.serviecs.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.tasks.TasksService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TasksServiceTestsCase {

    @Autowired
    private TasksService tasksService;
    @Autowired
    private TasksRepository tasksRepository;

    @Test
    void newTasksService_autowiredParameter_shouldReturnCreatedService() {
        Assertions.assertNotNull(tasksService);
    }

    @Test
    void createTask_newTask_shouldReturnTask() {
        Task task = Task.newTask();
        task = this.tasksService.createTask(task);
        Assertions.assertNotNull(task);
        Assertions.assertNotEquals(0L, task.getId());

        Task dbTask = this.tasksRepository.findById(task.getId()).get();
        Assertions.assertNotNull(dbTask);
        Assertions.assertEquals(task.getId(), dbTask.getId());
    }

    @Test
    void getTaskById_taskNotFound_shouldThrowNFE() {
        Assertions.assertThrows(NotFoundException.class, () -> this.tasksService.getTaskById(1L));
    }

    @Test
    void getTaskById_taskCreated_shouldReturnTask() {
        Task createdTask = this.tasksService.createTask(Task.newTask());
        Task foundedTask = this.tasksService.getTaskById(createdTask.getId());

        Assertions.assertNotNull(foundedTask);
        Assertions.assertNotEquals(0L, foundedTask.getId());
        Assertions.assertEquals(createdTask.getId(), foundedTask.getId());
    }

}
