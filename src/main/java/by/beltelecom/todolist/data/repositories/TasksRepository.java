package by.beltelecom.todolist.data.repositories;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class for work with {@link Task} objects in database.
 */
@Repository("tasksRepository")
public interface TasksRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUserOwner(User userOwner);

}
