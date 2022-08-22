package by.beltelecom.todolist.web.controllers.tasks;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.web.dto.TaskDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
public class TaskController {

    @Autowired
    private TasksService tasksService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class); // Logger;

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
        mav.addObject("taskDto", new TaskDto(task));

        return mav;
    }

    @GetMapping("/create-task")
    public ModelAndView getCreateTaskPage() {
        ModelAndView mav = new ModelAndView("create-task");
        mav.addObject("task", Task.newTask());

        return mav;
    }

    @PostMapping("/create-task")
    public String createTask(@ModelAttribute("task") Task aTask, HttpSession httpSession) {
        aTask.setDateCreation(LocalDate.now());

        // Get user session attribute:
        User user = (User) httpSession.getAttribute("userObj");

        Task createdTask = this.tasksService.createTask(aTask, user);

        return "redirect:/task?id=" +createdTask.getId();
    }

    @PostMapping("/delete-task")
    public String deleteTaskById(@ModelAttribute("delete-id") long aId) {

        // Delete task:
        this.tasksService.deleteById(aId);

        return "redirect:/tasks";
    }

    /**
     * Method handle http requests to edit, update any user's tasks at '/update-task' URL.
     * @param aTask - Modified {@link Task} model attribute;
     * @param httpSession - HTTP session;
     * @return - {@link String} redirect to task page.
     */
    @PostMapping("/update-task")
    public String updateTask(@ModelAttribute("task") Task aTask, HttpSession httpSession) {
        LOGGER.debug("Try to update Task[{}];", aTask);

        // Get user from session:
        User authenticatedUser = (User) httpSession.getAttribute("userObj");

        aTask.setOwner(authenticatedUser);
        aTask.setDateCreation(LocalDate.now());
        try {
            this.tasksService.updateTask(aTask);
        }catch (NotFoundException exc) {
            LOGGER.warn(exc.getMessage());
        }

        return "redirect:/task?id=" +aTask.getId();
    }
}
