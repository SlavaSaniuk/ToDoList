package by.beltelecom.todolist.services.security;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Default implementation of {@link UsersService} service bean.
 */
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository; // Repository bean (mapped in constructor);
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);

    public UsersServiceImpl(UsersRepository aUsersRepository) {
        this.usersRepository = aUsersRepository;
    }

    @Override
    @Transactional
    public User createUser(String aName) {
        LOGGER.debug("Try to create new user with name: {}", aName);

        // Check parameters:
        Objects.requireNonNull(aName, "Property[aName] of class[Users.class] must not be a <null>.");
        if (aName.length() == 0) throw new IllegalArgumentException("Property[aName] of class[Users.class] must not be an empty.");

        // Create new user:
        User user = new User();
        user.setName(aName);

        // Save user in db:
        return this.usersRepository.save(user);
    }
}
