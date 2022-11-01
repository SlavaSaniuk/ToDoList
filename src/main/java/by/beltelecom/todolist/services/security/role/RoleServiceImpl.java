package by.beltelecom.todolist.services.security.role;

import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.data.models.Role;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.RoleRepository;
import by.beltelecom.todolist.data.repositories.UsersRepository;
import by.beltelecom.todolist.data.wrappers.RoleWrapper;
import by.beltelecom.todolist.data.wrappers.UserWrapper;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link RoleService} service bean.
 */
public class RoleServiceImpl implements RoleService {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    // Spring beans:
    private final RoleRepository roleRepository;
    private final UsersRepository usersRepository;

    /**
     * Construct new {@link RoleServiceImpl} service bean.
     * @param aRoleRepository - {@link RoleRepository} repository bean.
     */
    public RoleServiceImpl(RoleRepository aRoleRepository, UsersRepository aUserRepository) {
        // Check arguments:
        ArgumentChecker.nonNull(aRoleRepository, "aRoleRepository");
        ArgumentChecker.nonNull(aUserRepository, "aUserRepository");

        LOGGER.debug(SpringLogging.Creation.createBean(RoleServiceImpl.class));

        // map arguments:
        this.roleRepository = aRoleRepository;
        this.usersRepository = aUserRepository;
    }

    /**
     * Implementation of {@link RoleService#addRoleToUser(UserRole, User)} method.
     * Method check if user exist in db by user id, create new role entity and save it.
     * @param aRole - {@link UserRole} user role.
     * @param aUser - {@link  User} for which to add a role.
     * @return - created role entity.
     * @throws NotFoundException - Throws in cases when specified user not found in database.
     */
    @Override
    @Transactional
    public Role addRoleToUser(UserRole aRole, User aUser) throws NotFoundException {
        // Check arguments:
        ArgumentChecker.nonNull(aRole, "aRole");
        ArgumentChecker.nonNull(aUser, "aUser");
        ArgumentChecker.idNotZero(UserWrapper.wrap(aUser));

        // Check if user exist:
        if (!this.usersRepository.existsById(aUser.getId())) throw new NotFoundException(aUser);

        // Create role and save it:
        LOGGER.debug(String.format("Add application Role[%s] to User[%s];", aRole, aUser));
        Role role = new RoleWrapper.RoleBuilder()
                .ofRole(aRole)
                .withRoleOwner(aUser)
                .build();

        return this.roleRepository.save(role);
    }

    /**
     * Implementation of {@link RoleService#findUserRoles(User)} method.
     * Method check if user exist in db and then return all user application roles.
     * Note: If user doesn't have any roles, method return empty list (Not null);
     * @param aUser - user roles owner.
     * @return - list of user roles.
     * @throws NotFoundException - Throws in cases when user not exist in db.
     */
    @Override
    public @NonNull List<Role> findUserRoles(User aUser) throws NotFoundException {
        // Check arguments:
        ArgumentChecker.nonNull(aUser, "aUser");

        // Check if user exist in database:
        if (!this.usersRepository.existsById(aUser.getId())) throw new NotFoundException(aUser);

        // Get user roles:
        LOGGER.debug(String.format("Find user[%s] application roles;", aUser));
        return new ArrayList<>(this.roleRepository.findAllByRoleOwner(aUser));
    }

}
