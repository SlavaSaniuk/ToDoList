package by.beltelecom.todolist.data.wrappers;

import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.data.models.Role;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.utilities.ArgumentChecker;

/**
 * {@link Wrapper} wrapper for {@link Role} entity.
 */
public class RoleWrapper implements Wrapper<Role>, Identification {

    // Class variables:
    private final Role role; // Wrapped role entity;

    /**
     * Construct new wrapped for specified role.
     * @param aRole - role to be wrapped.
     */
    private RoleWrapper(Role aRole) {
        ArgumentChecker.nonNull(aRole, "aRole");
        this.role = aRole;
    }

    /**
     * Construct new wrapper for specified role.
     * @param aRole - role to be wrapped.
     * @return - RoleWrapper instance.
     */
    public static RoleWrapper of(Role aRole) {
        return new RoleWrapper(aRole);
    }

    /**
     * Get wrapper role ID.
     * @return - wrapper role ID.
     */
    @Override
    public Number getIdentifier() {
        return this.role.getId();
    }

    /**
     * Unwrap wrapped role.
     * @return - wrapper role.
     */
    @Override
    public Role unwrap() {
        return this.role;
    }

    /**
     * Builder for {@link Role} entity.
     */
    public static class RoleBuilder {
        // Builder variables:
        private UserRole userRole;
        private User roleOwner;

        /**
         * Set user role for future role entity.
         * @param aRole - role to set.
         * @return - this builder.
         */
        public RoleBuilder ofRole(UserRole aRole) {
            this.userRole = aRole;
            return this;
        }

        /**
         * Set user owner for future role entity.
         * @param aUser - user owner.
         * @return - this builder.
         */
        public RoleBuilder withRoleOwner(User aUser) {
            this.roleOwner = aUser;
            return this;
        }

        /**
         * Build role entity.
         * @return - role entity.
         */
        public Role build() {
            Role build = new Role();
            build.setUserRole(this.userRole);
            build.setRoleOwner(this.roleOwner);
            return build;
        }
    }
}
