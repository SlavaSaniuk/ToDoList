package by.beltelecom.todolist.integration.serviecs.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.services.users.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TasksServiceTestCase {

    @Autowired
    private TasksService tasksService;

    @Autowired
    private UsersService usersService;

    @Test
    void newTasksService_autowiredParameter_shouldReturnCreatedService() {
        Assertions.assertNotNull(tasksService);
    }

    @Test
    void createTask_newUserAndTask_shouldCreateTask() {
        Task task = new Task();
        task.setName("Test name");

        User user = this.usersService.createUser("Test name");

        Task createdTask = this.tasksService.createTask(task, user);

        Assertions.assertNotNull(createdTask);
        Assertions.assertNotNull(createdTask.getOwner());
    }

    /*
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

    @Test
    void getAllTasks_emptyTasksTable_shouldReturnEmptyList() {
        List<Task> tasks = this.tasksService.getAllTasks();
        Assertions.assertNotNull(tasks);
        Assertions.assertEquals(0, tasks.size());
    }
    @Test
    void deleteById_idIsZero_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.tasksService.deleteById(0));
    }

    @Test
    void deleteById_idIsNonZero_shouldDeleteTask() {
        Task createdTask = this.tasksService.createTask(Task.newTask());
        long expectedId = createdTask.getId();
        Assertions.assertNotEquals(0L, expectedId);

        this.tasksService.deleteById(expectedId);
        Assertions.assertThrows(NotFoundException.class, () -> this.tasksService.getTaskById(expectedId));
    }

    @Test
    void updateTask_newName_shouldReturnTaskWithNewName() {
        String name = "UpdateTask test!";
        Task taskToSave = Task.newTask();
        taskToSave.setName(name);
        taskToSave = this.tasksService.createTask(taskToSave);
        Assertions.assertNotEquals(0L, taskToSave.getId());

        // Update task
        String expectedName = "New name for task!";
        taskToSave.setName(expectedName);
        Task updatedTask = this.tasksService.updateTask(taskToSave);

        Assertions.assertNotNull(updatedTask);
        Assertions.assertNotEquals(0L, updatedTask.getId());
        Assertions.assertNotNull(updatedTask.getName());
        Assertions.assertEquals(expectedName, updatedTask.getName());
    }

     */
}
