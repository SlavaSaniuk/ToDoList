package by.beltelecom.todolist.unit.services.security;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.UsersRepository;
import by.beltelecom.todolist.services.security.UsersServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
public class UsersServiceUnitTests {

    @MockBean
    private UsersRepository usersRepository;

    private UsersServiceImpl usersService;

    @BeforeEach
    void beforeEach() {
        this.usersService = new UsersServiceImpl(usersRepository);
    }

    @Test
    void createUser_emailIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, ()-> this.usersService.createUser(null));
    }

    @Test
    void createUser_emailIsempty_shouldThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> this.usersService.createUser(""));
    }

    @Test
    void createUser_normalUser_shouldReturnUser() {
        User user = new User();
        user.setName("anyName");

        Mockito.when(this.usersRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User created = this.usersService.createUser("anyName");
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.getName());
    }

}
