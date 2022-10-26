package by.beltelecom.todolist.data.converter;

public enum TaskStatus {

    WORKING(1),
    COMPLETED(2);

    private final int statusCode;

    TaskStatus(int aStatusCode) {
        this.statusCode = aStatusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

}
