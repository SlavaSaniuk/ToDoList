package by.beltelecom.todolist.web.controllers;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.services.tasks.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Controller
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping("/tasks")
    public String getTasks(Model aModel) {

        List<Task> tasks = this.tasksService.getAllTasks();
        aModel.addAttribute("tasks", tasks);

        return "tasks";
    }
}
