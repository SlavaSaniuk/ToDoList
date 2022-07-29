package by.beltelecom.todolist.services.tasks;

import by.beltelecom.todolist.data.models.Task;

import java.util.Objects;

public class TasksServiceImpl implements TasksService{

    @Override
    public Task getTaskById(long a_id) {
        return null;
    }

    @Override
    public Task createTask(Task aTask) {
        Objects.requireNonNull(aTask, "Tasks is <null>.");
        return aTask;
    }
}
