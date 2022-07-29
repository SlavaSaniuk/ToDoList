package by.beltelecom.todolist.web.controllers;

import by.beltelecom.todolist.data.models.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class TasksController {

    @GetMapping("/tasks")
    public String getTasks(Model aModel) {

        Task task = new Task(1);
        task.setName("ToDoList Application");
        task.setDescription("By Vyacheslav Saniuk.");
        task.setDateCreation(new Date());
        task.setDateCompletion(new Date());

        aModel.addAttribute("task", task);

        return "tasks";
    }
}
