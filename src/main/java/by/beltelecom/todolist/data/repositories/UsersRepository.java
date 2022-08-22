package by.beltelecom.todolist.data.repositories;

import by.beltelecom.todolist.data.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * {@link UsersRepository} repository bean used to work with entities objects in database.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    /**
     * Method search {@link User} entity object by its ID and return it with loaded {@link User#getTasks()} users tasks.
     * @param aId - user identificator;
     * @return - founded {@link User} entity object with users taks;
     */
    @Query("SELECT u FROM users u WHERE u.id = :id")
    @EntityGraph("User.tasks")
    User findUserByIdWithTasks(@Param("id") long aId);

}
