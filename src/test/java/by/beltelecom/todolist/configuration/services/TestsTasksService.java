package by.beltelecom.todolist.configuration.services;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.utilities.Randomizer;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class TestsTasksService  {


    private static final Logger LOGGER = LoggerFactory.getLogger(TestsTasksService.class); // Logger;
    // Spring beans:
    @Autowired
    private TasksService tasksService;
    // Class variables:
    @Getter
    private final List<Task> generatedTasks = new ArrayList<>();

    public Task createTask(User aUser) {
        LOGGER.debug("Try to generate task;");
        Task task = Task.newTask();
        task.setName(Randomizer.randomSentence(5));
        task = this.tasksService.createTask(task, aUser);

        this.generatedTasks.add(task);

        return task;
    }


}
