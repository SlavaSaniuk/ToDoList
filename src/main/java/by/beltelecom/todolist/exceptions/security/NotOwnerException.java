package by.beltelecom.todolist.exceptions.security;

import by.beltelecom.todolist.data.models.User;

/**
 * Exception occurs when user try to do any actions on object which does not belong to him.
 * Exception thrown in {@link by.beltelecom.todolist.services.security.owning.OwnerChecker#isUserOwn(User, Object)} method.
 */
public class NotOwnerException extends Exception {

    /**
     * Construct new {@link NotOwnerException} exception.
     * @param aUser - user.
     * @param aObj - any object.
     */
    public NotOwnerException(User aUser, Object aObj) {
        super(String.format("User[userId: %d] not owner of Object[%s] of type: %s;",
                aUser.getId(), aObj, aObj.getClass().getCanonicalName()));
    }

}
