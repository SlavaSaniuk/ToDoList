package by.beltelecom.todolist.web.dto.rest.task;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.web.dto.rest.ExceptionRestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Dto used to transfer list of user tasks.
 */
@Getter @Setter
@NoArgsConstructor
public class TasksListRestDto extends ExceptionRestDto {

    private long userOwnerId; // Owner tasks ID;
    private List<TaskRestDto> tasksList = new ArrayList<>(); // List of tasks;

    /**
     * Construct new {@link TasksListRestDto} DTO from specified list of tasks.
     * @param aTasksList - List of tasks.
     */
    public TasksListRestDto(List<Task> aTasksList) {
        super();

        if (aTasksList == null) return;
        aTasksList.forEach((task) -> this.tasksList.add(TaskRestDto.of(task)));
    }

    public TasksListRestDto(String anExceptionMsg, int anExceptionCode) {
        super(anExceptionMsg, anExceptionCode);
    }

    @Override
    public String toString() {
        return String.format("TasksListRestDto[userId: %d, tasksList: %s];", this.userOwnerId, this.tasksList);
    }
}
