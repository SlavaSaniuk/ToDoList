package by.beltelecom.todolist.services.security.role;

import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.data.models.Role;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.RoleRepository;
import by.beltelecom.todolist.data.wrappers.RoleWrapper;
import by.beltelecom.todolist.data.wrappers.UserWrapper;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link RoleService} service bean.
 */
public class RoleServiceImpl implements RoleService {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    // Spring beans:
    private final RoleRepository roleRepository;

    /**
     * Construct new {@link RoleServiceImpl} service bean.
     * @param aRoleRepository - {@link RoleRepository} repository bean.
     */
    public RoleServiceImpl(RoleRepository aRoleRepository) {
        // Check arguments:
        ArgumentChecker.nonNull(aRoleRepository, "aRoleRepository");

        LOGGER.debug(SpringLogging.Creation.createBean(RoleServiceImpl.class));

        // map arguments:
        this.roleRepository = aRoleRepository;
    }

    @Override
    public Role addRoleToUser(UserRole aRole, User aUser) {
        // Check arguments:
        ArgumentChecker.nonNull(aRole, "aRole");
        ArgumentChecker.nonNull(aUser, "aUser");
        ArgumentChecker.idNotZero(UserWrapper.wrap(aUser));

        // Create role and save it:
        LOGGER.debug(String.format("Add application Role[%s] to User[%s];", aRole, aUser));
        Role role = new RoleWrapper.RoleBuilder()
                .ofRole(aRole)
                .withRoleOwner(aUser)
                .build();

        return this.roleRepository.save(role);
    }
}
