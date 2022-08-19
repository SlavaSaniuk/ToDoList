package by.beltelecom.todolist.data.repositories;

import by.beltelecom.todolist.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM users u WHERE u.id = :id")
    User findUserByIdWithTasks(@Param("id") long aId);
}
