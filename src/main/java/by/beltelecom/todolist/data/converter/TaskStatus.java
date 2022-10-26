package by.beltelecom.todolist.data.converter;

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

}
