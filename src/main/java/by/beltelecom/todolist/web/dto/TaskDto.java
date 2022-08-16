package by.beltelecom.todolist.web.dto;

import by.beltelecom.todolist.data.models.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter @Setter
public class TaskDto implements DataTransferObject<Task> {

    private long id;
    private String name;
    private String description;
    private LocalDate dateOfCreation;
    private LocalDate dateOfCompletion;

    public TaskDto(Task aTask) {
        Objects.requireNonNull(aTask, "Argument [aTask] must be not null.");

        this.id = aTask.getId();
        this.name = aTask.getName();
        this.description = aTask.getDescription();
        this.dateOfCreation = aTask.getDateCreation();
        this.dateOfCompletion = aTask.getDateCompletion();
    }

    public String getHtmlFormatDateCompletion() {
        return this.dateOfCompletion.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public Task toEntity() {
        Task task = new Task();
        task.setId(this.id);
        task.setName(this.name);
        task.setDescription(this.description);
        task.setDateCreation(this.dateOfCreation);
        task.setDateCompletion(this.dateOfCompletion);

        return task;
    }
}
