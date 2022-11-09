package by.beltelecom.todolist.web.dto.rest.task;

import by.beltelecom.todolist.data.enums.TaskStatus;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.utilities.datetime.DateTimeUtilities;
import by.beltelecom.todolist.web.dto.DataTransferObject;
import by.beltelecom.todolist.web.dto.rest.ExceptionRestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskRestDto extends ExceptionRestDto implements DataTransferObject<Task> {

    private long taskId;
    private String taskName;
    private String taskDesc;
    private String dateOfCreation; // Task creation date;
    private String dateOfCompletion; // Task completion date;
    private int taskStatus; // Task status:

    public TaskRestDto(Task aTask) {
        // Map:
        this.taskId = aTask.getId();
        this.taskName = aTask.getName();
        this.taskDesc = aTask.getDescription();

        // Set date creation:
        this.dateOfCreation = DateTimeUtilities.dateToJsString(aTask.getDateCreation());

        // Set date completion:
        this.dateOfCompletion = DateTimeUtilities.dateToJsString(aTask.getDateCompletion());

        // Set task status:
        if (aTask.getTaskStatus() == null) this.taskStatus = TaskStatus.WORKING.getStatusCode();
        else this.taskStatus = aTask.getTaskStatus().getStatusCode();
    }

    public TaskRestDto(ExceptionRestDto exceptionRestDto) {
        super(exceptionRestDto.getExceptionMessage(), exceptionRestDto.getExceptionCode());
    }

    /**
     * Convert this DTO object to {@link Task} entity.
     * @return - entity.
     */
    @Override
    public Task toEntity() {

        // Set task status:
        TaskStatus taskStatus;
        try {
            taskStatus = TaskStatus.of(this.taskStatus);
        }catch (IllegalArgumentException e) {
            taskStatus = TaskStatus.WORKING;
        }

        // Create task instance:
        return new TaskWrapper.Builder()
                .ofId(this.taskId)
                .withName(this.taskName)
                .withDescription(this.taskDesc)
                .withDateOfCreation(this.jsDateToStrOrElse(this.dateOfCreation, LocalDate.now()))
                .withDateOfCompletion(this.jsDateToStrOrElse(this.dateOfCompletion, LocalDate.now()))
                .withStatus(taskStatus)
                .build();
    }

    public static TaskRestDto of(Task aTask) {
        TaskRestDto dto = new TaskRestDto();
        dto.setTaskId(aTask.getId());
        dto.setTaskName(aTask.getName());
        dto.setTaskDesc(aTask.getDescription());

        // Set date creation:
        dto.setDateOfCreation(DateTimeUtilities.dateToJsString(aTask.getDateCreation()));
        // Set date completion:
        dto.setDateOfCompletion(DateTimeUtilities.dateToJsString(aTask.getDateCompletion()));
        // Set task status:
        if (aTask.getTaskStatus() == null) dto.setTaskStatus(TaskStatus.WORKING.getStatusCode());
        else dto.setTaskStatus(aTask.getTaskStatus().getStatusCode());

        return dto;
    }

    /**
     * Convert specified string to {@link LocalDate} date, or if specified string is null or not parseable return
     * else date value.
     * ExceptionSafe.
     * @param aJsDateToStr - JS Date string.
     * @param aElseDate - default date value.
     * @return - date.
     */
    public LocalDate jsDateToStrOrElse(String aJsDateToStr, LocalDate aElseDate) {
        LocalDate date = DateTimeUtilities.ExcSafe.jsDateStrToDate(aJsDateToStr);
        return date == null ? aElseDate : date;
    }

}
