package by.beltelecom.todolist.web.dto;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.utilities.logging.Checks;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * {@link UserDto} class implemented {@link DataTransferObject} object interface and
 * encapsulate {@link by.beltelecom.todolist.data.models.User} entity object.
 */
@Getter @Setter
@NoArgsConstructor
public class UserDto implements DataTransferObject<User> {

    private long userId;
    private String userName;

    private UserDto(User aUser) {
        // Check parameters
        Objects.requireNonNull(aUser, Checks.argumentNotNull("aUser", User.class));

        // Maps properties:
        this.userId = aUser.getId();
        this.userName = aUser.getName();
    }

    @Override
    public User toEntity() {
        User user = new User();
        user.setId(this.userId);
        user.setName(this.userName);

        return user;
    }
}
