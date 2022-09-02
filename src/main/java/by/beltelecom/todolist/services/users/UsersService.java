package by.beltelecom.todolist.services.users;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * UsersService service bean used to manipulate {@link User} entities objects in application.
 */
public interface UsersService {


    /**
     * Get {@link User} entity object from database.
     * @param aId - user's id;
     * @return - user entity object;
     * @throws NotFoundException - throws in cases, when user entity not founded in database.
     */
    User getUserById(long aId) throws NotFoundException;

    /**
     * Create new {@link User} entity object and persist it in database.
     * @see by.beltelecom.todolist.security.authentication.SignService#registerAccount(Account, User);
     * @param aName - required user name;
     * @return - created user entity object.
     */
    @Transactional
    User createUser(String aName);

    /**
     * Delete specified {@link User} entiti object with their account.
     * @param aUser - {@link User} to delete.
     */
    @Transactional
    void deleteUser(User aUser);
}
