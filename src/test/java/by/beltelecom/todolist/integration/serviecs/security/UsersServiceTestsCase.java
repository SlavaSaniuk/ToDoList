package by.beltelecom.todolist.integration.serviecs.security;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.services.security.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UsersServiceTestsCase {

    @Autowired
    private UsersService usersService;

    @Test
    void createUser_nameString_shouldReturnCreatedUser() {
        String name = "testName";
        User user = this.usersService.createUser(name);

        Assertions.assertNotNull(user);
        Assertions.assertNotEquals(0L, user.getId());
        Assertions.assertEquals(name, user.getName());
    }
}
