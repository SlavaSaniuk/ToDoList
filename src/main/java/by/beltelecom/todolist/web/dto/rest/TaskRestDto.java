package by.beltelecom.todolist.web.dto.rest;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.web.dto.DataTransferObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@ToString
public class TaskRestDto extends ExceptionRestDto implements DataTransferObject<Task> {

    private long taskId;
    private String taskName;

    public TaskRestDto(Task aTask) {
        // Map:
        this.taskId = aTask.getId();
        this.taskName = aTask.getName();
    }

    @Override
    public Task toEntity() {
        Task task = Task.newTask();

        task.setId(this.taskId);
        task.setName(this.taskName);

        return task;
    }
}
