package by.beltelecom.todolist.web.controllers;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.tasks.TasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class TaskController {

    @Autowired
    private TasksService tasksService; // Tasks service bean;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

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
    public String createTask(@ModelAttribute("task") Task aTask, HttpServletRequest request) {
        LOGGER.warn("Task name:" +aTask.getName());
        LOGGER.warn("Task desc:" +aTask.getDescription());
        aTask.setDateCreation(new Date());
        Task createdTask = this.tasksService.createTask(aTask);

        return "redirect:/task?id=" +createdTask.getId();
    }


}
