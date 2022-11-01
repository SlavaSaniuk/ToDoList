package by.beltelecom.todolist.services.security.role;

import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.data.models.Role;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

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
}
