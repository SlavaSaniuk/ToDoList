package by.beltelecom.todolist.services.security.role;

import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.data.models.Role;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service bean that manage user application roles.
 */
public interface RoleService {

    /**
     * Add application role to specified user.
     * @param aRole - {@link UserRole} user role.
     * @param aUser - {@link  User} for which to add a role.
     * @return - Added {@link Role} role entity.
     * @throws NotFoundException - if user not exist in db.
     */
    @Transactional
    Role addRoleToUser(UserRole aRole, User aUser) throws NotFoundException;

    /**
     * Get application roles of specified user.
     * Note: If user doesn't have any roles, method return empty list (Not null);
     * @param aUser - user roles owner.
     * @return - list of user roles.
     * @throws NotFoundException - Throws in cases when user not exist in db.
     */
    List<Role> findUserRoles(User aUser) throws NotFoundException;
}
