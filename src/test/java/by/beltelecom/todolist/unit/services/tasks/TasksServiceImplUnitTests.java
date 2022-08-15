package by.beltelecom.todolist.unit.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.services.tasks.TasksServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TasksServiceImplUnitTests {

    private TasksService taskService;

    @MockBean
    private TasksRepository tasksRepository;


    @BeforeEach
    public void beforeAll() {
        this.taskService = new TasksServiceImpl(this.tasksRepository);
    }

    @Test
    void createTask_aTaskIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> this.taskService.createTask(null));
    }

    @Test
    void newTasksServiceImpl_aRepositoryIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class,() -> new TasksServiceImpl(null));
    }
    @Test
    void createTask_newTask_shouldReturnCreatedTasksWithIdParameter() {
        Task dbTask = Task.newTask();
        dbTask.setId(1L);
        Mockito.when(tasksRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(dbTask);

        Task testTask = Task.newTask();
        testTask = this.taskService.createTask(testTask);

        Assertions.assertNotEquals(0L, testTask.getId());
    }

    @Test
    void getTasksById_idIsZero_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.taskService.getTaskById(0L));
    }
    @Test
    void getTaskById_taskNotFound_shouldThrowNFE() {
        Mockito.when(this.tasksRepository.findById(ArgumentMatchers.anyLong())).thenThrow(NotFoundException.class);
        Assertions.assertThrows(NotFoundException.class, () -> this.taskService.getTaskById(1L));
    }
    @Test
    void getTaskById_newTask_shouldReturnTaskWithId() {
        long expected_id = 1L;
        Task expectedTask = new Task();
        expectedTask.setId(expected_id);
        Mockito.when(this.tasksRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(expectedTask);

        Task createdTask = this.taskService.createTask(Task.newTask());
        Assertions.assertNotNull(createdTask);
        Assertions.assertEquals(expected_id, createdTask.getId());

        Mockito.when(this.tasksRepository.findById(expected_id)).thenReturn(Optional.of(createdTask));

        Task foundedTask = this.taskService.getTaskById(expected_id);
        Assertions.assertNotNull(foundedTask);
        Assertions.assertEquals(expected_id, foundedTask.getId());

    }

    @Test
    void updateEntity_entityWithoutId_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> this.taskService.updateEntity(Task.newTask()));
    }

    @Test
    void updateEntity_entityNofFoundInDb_shouldThrowNFE() {
        Mockito.when(this.tasksRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
        Task task = Task.newTask();
        task.setId(55L);
        Assertions.assertThrows(NotFoundException.class, () -> this.taskService.updateEntity(Task.newTask()));
    }

}
