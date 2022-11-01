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

import java.util.List;

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

    @Test
    void findUserRoles_userHaveFourRoles_shouldReturnListOfUserRoles() {
        // Generate user and roles:
        User user = this.testsUserService.testingUser("findUserRoles1").getUser();
        try {
            this.roleService.addRoleToUser(UserRole.ROLE_ADMIN, user);
            this.roleService.addRoleToUser(UserRole.ROLE_ROOT_ADMIN, user);
            this.roleService.addRoleToUser(UserRole.ROLE_USER, user);
            this.roleService.addRoleToUser(UserRole.ROLE_AUTHENTICATED_USER, user);
        } catch (NotFoundException e) {
            Assertions.fail();
        }

        try {
            List<Role> fondedRoles = this.roleService.findUserRoles(user);
            Assertions.assertNotNull(fondedRoles);
            Assertions.assertFalse(fondedRoles.isEmpty());
            Assertions.assertEquals(4, fondedRoles.size());

            LOGGER.debug(String.format("Founded user[%s] roles:", user));
            fondedRoles.forEach(role -> LOGGER.debug(String.format("\t Role[%s];", role)));
        } catch (NotFoundException e) {
            Assertions.fail();
        }

    }

    @Test
    void findUserRoles_userDoesNotHaveAnyRoles_shouldReturnEmptyList() {
        // Generate user:
        User user = this.testsUserService.testingUser("findUserRoles2").getUser();

        try {
            List<Role> fondedRoles = this.roleService.findUserRoles(user);
            Assertions.assertNotNull(fondedRoles);
            Assertions.assertTrue(fondedRoles.isEmpty());
        } catch (NotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void findUserRoles_userNotExistInDb_shouldThrowNFE() {
        User user = new User();
        Assertions.assertThrows(NotFoundException.class, () -> this.roleService.findUserRoles(user));
    }

}
