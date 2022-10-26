package by.beltelecom.todolist.unit.services.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.exceptions.RuntimeNotFoundException;
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
    void getTasksById_idIsZero_shouldThrowIAE() {
        Assertions.assertThrows(RuntimeNotFoundException.class, () -> this.taskService.getTaskById(0L));
    }
    @Test
    void getTaskById_taskNotFound_shouldThrowNFE() {
        Mockito.when(this.tasksRepository.findById(ArgumentMatchers.anyLong())).thenThrow(RuntimeNotFoundException.class);
        Assertions.assertThrows(RuntimeNotFoundException.class, () -> this.taskService.getTaskById(1L));
    }

    @Test
    void updateEntity_entityWithoutId_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> this.taskService.updateTask(TaskWrapper.Creator.createTask()));
    }

    @Test
    void updateEntity_entityNofFoundInDb_shouldThrowNFE() {
        Mockito.when(this.tasksRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);

        Task task = TaskWrapper.Creator.createTask();
        task.setId(55L);

        Assertions.assertThrows(RuntimeNotFoundException.class, () -> this.taskService.updateTask(task));
    }

}
