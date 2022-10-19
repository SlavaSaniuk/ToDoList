package by.beltelecom.todolist.services.security.owning;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.security.NotOwnerException;

public interface OwnerChecker<T> {

    void isUserOwn(User aUser, T aT) throws NotOwnerException;
}
