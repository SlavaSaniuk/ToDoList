package by.beltelecom.todolist.unit.services.tasks;

import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.services.tasks.TasksServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TasksServiceImplUnitTests {

    private TasksService taskService;

    @BeforeEach
    public void beforeAll() {
        this.taskService = new TasksServiceImpl();
    }

    @Test
    void createTask_aTaskIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> this.taskService.createTask(null));
    }
}
