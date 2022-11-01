package by.beltelecom.todolist.integration.services.security.role;

import by.beltelecom.todolist.configuration.ServicesTestsConfiguration;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.data.models.Role;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.security.role.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(ServicesTestsConfiguration.class)
public class RoleServiceTestsCase {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceTestsCase.class);
    // Spring beans:
    @Autowired
    private RoleService roleService;
    @Autowired
    private TestsUserService testsUserService;

    @Test
    void addRoleToUser_newUser_shouldAddRoleToUser() throws NotFoundException {
        // Generate user:
        User user = this.testsUserService.testingUser("addRoleToUser1").getUser();

        Role created = this.roleService.addRoleToUser(UserRole.ROLE_USER, user);
        Assertions.assertNotNull(created);
        Assertions.assertNotEquals(0L, created.getId());

        LOGGER.debug(String.format("Added role: %s;", created));
    }

    @Test
    void addRoleToUser_userNotfound_shouldThrowNfe() {
        User user = new User();
        user.setName("addRoleToUser2");
        user.setId(678349);

        Assertions.assertThrows(NotFoundException.class, () -> this.roleService.addRoleToUser(UserRole.ROLE_USER, user));
    }


}
