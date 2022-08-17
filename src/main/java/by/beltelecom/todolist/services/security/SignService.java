package by.beltelecom.todolist.services.security;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SignService")
public interface SignService {

    @Transactional
    Account registerAccount(Account anAccount, User aUser);

}
