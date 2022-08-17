package by.beltelecom.todolist.services.security;

import by.beltelecom.todolist.data.models.User;
import org.springframework.transaction.annotation.Transactional;

public interface UsersService {

    @Transactional
    User createUser(String aName);
}
