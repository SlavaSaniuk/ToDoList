package by.beltelecom.todolist.data.wrappers;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.enums.TaskStatus;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.printer.ListUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Wrapper for {@link Task} task object.
 * Wrapper provide methods for work with wrapped object and other static method.
 */
public class TaskWrapper implements Identification {

    // Class variables:
    private final Task task; // Wrapper object;
    private static final Printer PRINTER = new Printer(); // Printer inner static class instance;

    /**
     * Getter for {@link Printer} instance.
     * @return - Printer instance.
     */
    public static Printer printer() {
        return TaskWrapper.PRINTER;
    }

    private TaskWrapper(Task aTask) {
        this.task = aTask;
    }

    public static TaskWrapper wrap(Task aTask) {
        return new TaskWrapper(aTask);
    }

    @Override
    public Number getIdentifier() {
        return this.task.getId();
    }

    /**
     * Static inner class Printer used to convert any {@link Task} task objects in string.
     */
    public static class Printer {

        /**
         * Convert source task to string (only task ID property).
         * @param aTask - source task.
         * @return - String in format: "Task[id: TASK_ID]";
         */
        public String taskId(Task aTask) {
            ArgumentChecker.nonNull(aTask, "aTask");
            return String.format("Task[id: %d]", aTask.getId());
        }

        /**
         * Convert source list of tasks to string (only task ID property).
         * @param aListOfTasks - source list of tasks.
         * @return - String in format "List[Task[id: 1], Task[id: 2], Task[id: 3], Task[id: 4]]";
         */
        public String listOfTaskId(List<Task> aListOfTasks) {

            ArgumentChecker.nonNull(aListOfTasks, "aListOfTasks");
            List<Object> listOfTasks = new ArrayList<>(aListOfTasks);

            return ListUtils.listToString(listOfTasks, aObj -> TaskWrapper.printer().taskId((Task) aObj));
        }

    }

    public static class Creator {

        public static Task createTask() {
            Task task = new Task();
            task.setDateCreation(LocalDate.now());

            // Set default task status WORKING:
            task.setTaskStatus(TaskStatus.WORKING);

            return task;
        }

        public static Task createTask(String aName) {
            Task task = TaskWrapper.Creator.createTask();
            task.setName(aName);
            return task;
        }

        public static Task createTask(long aId) {
            Task task = TaskWrapper.Creator.createTask();
            task.setId(aId);
            return task;
        }

    }

    /**
     * Builder for {@link Task} task entities.
     */
    public static class Builder {

        // Class variables:
        private long taskId;
        private String taskName;
        private String taskDescription;
        private LocalDate dateOfCreation;
        private TaskStatus taskStatus = TaskStatus.WORKING;

        public Builder ofId(long aId) {
            this.taskId = aId;
            return this;
        }

        public Builder withName(String aName) {
            this.taskName = aName;
            return this;
        }

        public Builder withDescription(String aDesc) {
            this.taskDescription = aDesc;
            return this;
        }

        public Builder withStatus(TaskStatus aStatus) {
            this.taskStatus = aStatus;
            return this;
        }

        public Builder withDateOfCreation(LocalDate aDate) {
            this.dateOfCreation = aDate;
            return this;
        }

        public Task build() {
            Task task = new Task();
            // Set properties:
            task.setId(this.taskId);
            task.setName(this.taskName);
            task.setDescription(this.taskDescription);
            task.setTaskStatus(this.taskStatus);

            // Set conditional properties:
            task.setDateCreation(Objects.requireNonNullElseGet(this.dateOfCreation, LocalDate::now));

            return task;
        }
    }

}
