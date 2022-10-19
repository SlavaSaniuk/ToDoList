package by.beltelecom.todolist.services.security.owning;

import by.beltelecom.todolist.data.models.User;

public interface OwnerChecker<T> {

    boolean isUserOwn(User aUser, T aT);
}
