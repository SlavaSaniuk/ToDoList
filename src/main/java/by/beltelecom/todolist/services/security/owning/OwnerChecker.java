package by.beltelecom.todolist.services.security.owning;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;

/**
 * Type safe security service bean user to check if user own T entity object.
 * @param <T> - Entity type.
 */
public interface OwnerChecker<T> {

    /**
     * Check if user own specified entity object.
     * @param aUser - user owner.
     * @param aT - any entity object.
     * @throws NotOwnerException - Throws in cases when user not own entity object.
     * @throws NotFoundException - Throws in cases when specified entity object not exist in database.
     */
    void isUserOwn(User aUser, T aT) throws NotOwnerException, NotFoundException;

}
