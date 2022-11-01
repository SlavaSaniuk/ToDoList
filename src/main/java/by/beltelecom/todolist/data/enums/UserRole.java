package by.beltelecom.todolist.data.enums;

import lombok.Getter;

/**
 * This enum define different application roles.
 */
public enum UserRole {

    // ***************** ADMINISTRATOR ROLES *******************
    ROLE_ROOT_ADMIN("ROLE_ROOT_ADMIN"),
    ROLE_ADMIN("ROLE_ADMIN"),

    // ********************** USER ROLES ***********************
    ROLE_AUTHENTICATED_USER("ROLE_AUTHENTICATED_USER"),
    ROLE_USER("ROLE_USER");

    // Class variables:
    @Getter
    private final String roleName; // Role name;

    /**
     * Construct new application role.
     * @param aRoleName - role name.
     */
    UserRole(String aRoleName) {
        this.roleName = aRoleName;
    }

    @Override
    public String toString() {
        return String.format("Role[name: %s]", this.roleName);
    }
}
