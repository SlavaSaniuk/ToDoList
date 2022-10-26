package by.beltelecom.todolist.data.enums;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public enum TaskStatus {

    WORKING(1, "WORKING"),
    COMPLETED(2, "COMPLETED");

    private final int statusCode;
    private final String statusName;

    TaskStatus(int aStatusCode, String aStatusName) {
        this.statusCode = aStatusCode;
        this.statusName = aStatusName;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public static TaskStatus of(int aStatusCode) throws IllegalArgumentException {
        Optional<TaskStatus> taskStatusOpt = Arrays.stream(TaskStatus.values()).filter((status -> status.getStatusCode() == aStatusCode)).findFirst();
        try {
            return taskStatusOpt.orElseThrow();
        }catch (NoSuchElementException e) {
            throw new IllegalArgumentException(String.format("Task status of code[%d] is not exist.", aStatusCode));
        }
    }
}
