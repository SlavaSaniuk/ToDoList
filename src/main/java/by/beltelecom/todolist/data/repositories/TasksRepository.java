package by.beltelecom.todolist.data.repositories;

import by.beltelecom.todolist.data.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class for work with {@link Task} objects in database/
 */
@Repository("tasksRepository")
public interface TasksRepository extends CrudRepository<Task, Long> {
}
