package by.beltelecom.todolist.data.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Constants represent task statuses.
 */
@Getter
public enum TaskStatus {

    /**
     * Task in work now.
     */
    WORKING(1, "WORKING"),
    /**
     * Task already completed.
     */
    COMPLETED(2, "COMPLETED");

    private final int statusCode;
    private final String statusName;

    TaskStatus(int aStatusCode, String aStatusName) {
        this.statusCode = aStatusCode;
        this.statusName = aStatusName;
    }


    /**
     * Return task status of specified code.
     * @param aStatusCode - status code.
     * @return - TaskStatus object.
     * @throws IllegalArgumentException - There are not statuses of specified code.
     */
    public static TaskStatus of(int aStatusCode) throws IllegalArgumentException {
        Optional<TaskStatus> taskStatusOpt = Arrays.stream(TaskStatus.values()).filter((status -> status.getStatusCode() == aStatusCode)).findFirst();
        try {
            return taskStatusOpt.orElseThrow();
        }catch (NoSuchElementException e) {
            throw new IllegalArgumentException(String.format("Task status of code[%d] is not exist.", aStatusCode));
        }
    }
}
