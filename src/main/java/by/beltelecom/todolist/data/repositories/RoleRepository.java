package by.beltelecom.todolist.data.repositories;

import by.beltelecom.todolist.data.models.Role;
import by.beltelecom.todolist.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository data bean work with {@link Role} entities in database.
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find user role by role owner.
     * @param aRoleOwner - user owner.
     * @return - list of user roles.
     */
    List<Role> findAllByRoleOwner(User aRoleOwner);
}
