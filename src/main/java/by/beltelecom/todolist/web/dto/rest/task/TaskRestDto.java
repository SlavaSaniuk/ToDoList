package by.beltelecom.todolist.web.dto.rest.task;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.web.dto.DataTransferObject;
import by.beltelecom.todolist.web.dto.rest.ExceptionRestDto;
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
    private String taskDesc;

    public TaskRestDto(Task aTask) {
        // Map:
        this.taskId = aTask.getId();
        this.taskName = aTask.getName();
        this.taskDesc = aTask.getDescription();
    }

    @Override
    public Task toEntity() {
        Task task = Task.newTask();

        task.setId(this.taskId);
        task.setName(this.taskName);
        task.setDescription(this.taskDesc);

        return task;
    }

    public static TaskRestDto of(Task aTask) {
        TaskRestDto dto = new TaskRestDto();
        dto.setTaskId(aTask.getId());
        dto.setTaskName(aTask.getName());
        dto.setTaskDesc(aTask.getDescription());
        return dto;
    }
}
