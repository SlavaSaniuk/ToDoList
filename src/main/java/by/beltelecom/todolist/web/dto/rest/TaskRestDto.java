package by.beltelecom.todolist.web.dto.rest;

import by.beltelecom.todolist.data.models.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TaskRestDto extends ExceptionRestDto {

    private long taskId;
    private String taskName;

    public TaskRestDto(Task aTask) {
        // Map:
        this.taskId = aTask.getId();
        this.taskName = aTask.getName();
    }
}
