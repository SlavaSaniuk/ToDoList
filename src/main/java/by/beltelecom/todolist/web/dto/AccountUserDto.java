package by.beltelecom.todolist.web.dto;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * DTO class which can handle {@link Account} and {@link by.beltelecom.todolist.data.models.User} entity.
 * User at: sign.html
 */
@Getter @Setter
@NoArgsConstructor
public class AccountUserDto  implements DataTransferObject<Account> {

    private long accountId;
    private String accountEmail;
    private String accountPassword;
    private long userId;
    private String userName;

    /**
     * Create DTO from existing {@link Account} account;
     * @param anAccount - existing account;
     */
    public AccountUserDto(Account anAccount) {
        Objects.requireNonNull(anAccount, "[Account] parameter can not be <null>.");
        // Map account:
        this.accountId = anAccount.getId();
        this.accountEmail = anAccount.getEmail();
        this.accountPassword = anAccount.getPassword();
    }

    /**
     * Create DTO from existing {@link User} account;
     * @param aUser - existing user;
     */
    public AccountUserDto(User aUser) {
        Objects.requireNonNull(aUser, "[User] parameter can not be <null>.");
        //Map user:
        this.userId = aUser.getId();
        this.userName = aUser.getName();
    }

    /**
     * Create DTO from existing {@link Account} account and {@link User} user;
     * @param anAccount - existing account;
     * @param aUser - existing user;
     */
    public AccountUserDto(Account anAccount, User aUser) {
        Objects.requireNonNull(anAccount, "[Account] parameter can not be <null>.");
        Objects.requireNonNull(aUser, "[User] parameter can not be <null>.");
        // Map account:
        this.accountId = anAccount.getId();
        this.accountEmail = anAccount.getEmail();
        this.accountPassword = anAccount.getPassword();
        //Map user:
        this.userId = aUser.getId();
        this.userName = aUser.getName();
    }

    @Override
    public Account toEntity() {
        Account account = new Account();
        account.setId(this.accountId);
        account.setEmail(this.accountEmail);
        account.setPassword(this.accountPassword);

        return account;
    }

    /**
     * Create {@link User} user entity from this DTO.
     * @return - User entity;
     */
    public User toUser() {
        User user = new User();
        user.setId(this.userId);
        user.setName(this.userName);

        return user;
    }
}
