package by.beltelecom.todolist.services.users;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.UsersRepository;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.utilities.logging.Checks;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of {@link UsersService} service bean.
 */
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository; // Repository bean (mapped in constructor);
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);

    /**
     * Contruct new {@link UsersServiceImpl} service bean.
     * @param aUsersRepository - users repository.
     */
    public UsersServiceImpl(UsersRepository aUsersRepository) {
        LOGGER.debug(SpringLogging.Creation.createBean(UsersServiceImpl.class));
        this.usersRepository = aUsersRepository;
    }

    @Override
    public User getUserById(long aId) throws NotFoundException {
        LOGGER.debug("Try to get user entity[{}] from database;", aId);

        Optional<User> userOptional = this.getUserOptById(aId);
        if(userOptional.isPresent()) return userOptional.get();
        else throw new NotFoundException(User.class);
    }

    /**
     * Find user in database by id;
     * @param aId - user's id;
     * @return - {@link Optional} of {@link User} entity.
     */
    public Optional<User> getUserOptById(long aId) {
        LOGGER.debug("Try to get user[{}] optional from database;", aId);

        // Check arguments:
        if (aId == 0L) throw new IllegalArgumentException(Checks.Numbers.argNotZero("aId", Long.class));

        return this.usersRepository.findById(aId);
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

    @Override
    public void deleteUser(User aUser) {
        Objects.requireNonNull(aUser, Checks.argumentNotNull("aUser", User.class));
        if (aUser.getId() == 0L) throw new IllegalArgumentException(Checks.Numbers.argNotZero("id", Long.class));

        LOGGER.debug("Try to delete user: {};" , aUser);
        this.usersRepository.deleteById(aUser.getId());
    }
}
