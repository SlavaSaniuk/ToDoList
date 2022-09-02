package by.beltelecom.todolist.configuration.bean;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TestUser {

    private User user; // User entity

    private Account account; // User account

    private String jwtToken; // JWT Token

}
