package by.beltelecom.todolist.data.repositories;

import by.beltelecom.todolist.data.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository data bean work with {@link Role} entities in database.
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {

}
