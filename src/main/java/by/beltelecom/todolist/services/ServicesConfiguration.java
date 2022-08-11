package by.beltelecom.todolist.services;

import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.services.tasks.TasksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    private TasksRepository tasksRepository;

    @Bean("taskService")
    public TasksService tasksService() {
        return new TasksServiceImpl(this.tasksRepository);

    }

    @Autowired
    public void setTasksRepository(TasksRepository aTaskRepository) {
        this.tasksRepository = aTaskRepository;
    }
}
