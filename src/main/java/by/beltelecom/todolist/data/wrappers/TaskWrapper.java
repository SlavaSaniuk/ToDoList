package by.beltelecom.todolist.data.wrappers;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.enums.TaskStatus;

import java.time.LocalDate;
import java.util.Objects;

public class TaskWrapper implements Identification {

    // Class variables:
    private final Task task;
    private final TaskPrinter printer = new TaskPrinter();

    public TaskPrinter printer() {
        return this.printer;
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

    public class TaskPrinter {

        public String toString() {
            return task.toString();
        }

        public String toStringWithUser() {
            StringBuilder sb = new StringBuilder(task.toString().substring(0, task.toString().length()-1));

            // Append user owner if existed:
            if (task.getOwner() != null) sb.append(String.format(", owner: %s", task.getOwner()));

            return sb.append("]").toString();
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
