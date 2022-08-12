package by.beltelecom.todolist.data.repositories;

import by.beltelecom.todolist.data.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tasksRepository")
public interface TasksRepository extends CrudRepository<Task, Long> {
}
