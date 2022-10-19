package by.beltelecom.todolist.unit.data.models;

import by.beltelecom.todolist.data.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserUnitTests {

    @Test
    void equals_usersIdAreSame_shouldReturnTrue() {
        long sameId = 1L;

        User user1 = new User();
        user1.setId(sameId);

        User user2 = new User();
        user2.setId(sameId);

        Assertions.assertEquals(user1, user2);
    }

    @Test
    void equals_usersIdAreNotSame_shouldReturnFalse() {
        User user1 = new User();
        user1.setId(2L);

        User user2 = new User();
        user2.setId(3L);

        Assertions.assertNotEquals(user1, user2);
    }

    @Test
    void equals_otherUserObjIsNull_shouldReturnFalse() {
        User user = new User();
        user.setId(1L);

        Assertions.assertNotEquals(user, null);
    }

    @Test
    void equals_otherObjClassNotUserClass_shouldReturnFalse() {
        User user = new User();
        user.setId(1L);
        Assertions.assertNotEquals(user, new Object());
    }
}
