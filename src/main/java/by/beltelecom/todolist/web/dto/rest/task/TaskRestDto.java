package by.beltelecom.todolist.web.dto.rest.task;

import by.beltelecom.todolist.data.enums.TaskStatus;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
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
    private int taskStatus; // Task status:

    public TaskRestDto(Task aTask) {
        // Map:
        this.taskId = aTask.getId();
        this.taskName = aTask.getName();
        this.taskDesc = aTask.getDescription();

        if (aTask.getTaskStatus() == null) this.taskStatus = TaskStatus.WORKING.getStatusCode();
        else this.taskStatus = aTask.getTaskStatus().getStatusCode();
    }

    public TaskRestDto(ExceptionRestDto exceptionRestDto) {
        super(exceptionRestDto.getExceptionMessage(), exceptionRestDto.getExceptionCode());
    }

    @Override
    public Task toEntity() {

        // Set task status:
        TaskStatus taskStatus;
        try {
            taskStatus = TaskStatus.of(this.taskStatus);
        }catch (IllegalArgumentException e) {
            taskStatus = TaskStatus.WORKING;
        }

        return new TaskWrapper.Builder()
                .ofId(this.taskId)
                .withName(this.taskName)
                .withDescription(this.taskDesc)
                .withStatus(taskStatus)
                .build();
    }

    public static TaskRestDto of(Task aTask) {
        TaskRestDto dto = new TaskRestDto();
        dto.setTaskId(aTask.getId());
        dto.setTaskName(aTask.getName());
        dto.setTaskDesc(aTask.getDescription());

        if (aTask.getTaskStatus() == null) dto.setTaskStatus(TaskStatus.WORKING.getStatusCode());
        else dto.setTaskStatus(aTask.getTaskStatus().getStatusCode());

        return dto;
    }

}
