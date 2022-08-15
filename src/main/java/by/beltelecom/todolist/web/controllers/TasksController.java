package by.beltelecom.todolist.web.controllers;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.tasks.TasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TasksController {

    @Autowired
    private TasksService tasksService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class); // Logger;

    @GetMapping("/tasks")
    public String getTasks(Model aModel) {

        List<Task> tasks = this.tasksService.getAllTasks();
        aModel.addAttribute("tasks", tasks);

        return "tasks";
    }


    @GetMapping("/task")
    public ModelAndView getTaskPage(@RequestParam(value = "id") long aId) {
        // Create model and view:
        ModelAndView mav = new ModelAndView("task");

        // Get task from db by id:
        Task task = null;
        try {
            task = this.tasksService.getTaskById(aId);
        }catch (NotFoundException exc) {
            LOGGER.warn(exc.getMessage());
        }

        mav.addObject("task", task);
        return mav;
    }

    @GetMapping("/create-task")
    public ModelAndView getCreateTaskPage() {
        ModelAndView mav = new ModelAndView("create-task");
        mav.addObject("task", Task.newTask());

        return mav;
    }

    @PostMapping("/create-task")
    public String createTask(@ModelAttribute("task") Task aTask) {
        aTask.setDateCreation(LocalDate.now());
        LOGGER.warn("date of completion: " +aTask.getDateCreation().toString());
        Task createdTask = this.tasksService.createTask(aTask);

        return "redirect:/task?id=" +createdTask.getId();
    }

    @PostMapping("/delete-task")
    public String deleteTaskById(@ModelAttribute("delete-id") long aId) {

        // Delete task:
        this.tasksService.deleteById(aId);

        return "redirect:/tasks";
    }

    @PostMapping("/update-task")
    public String updateTask(@ModelAttribute("task") Task aTask) {
        aTask.setDateCreation(LocalDate.now());
        try {
            this.tasksService.updateTask(aTask);
        }catch (NotFoundException exc) {
            LOGGER.warn(exc.getMessage());
        }

        return "redirect:/task?id=" +aTask.getId();
    }
}
