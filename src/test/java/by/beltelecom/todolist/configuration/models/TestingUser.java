package by.beltelecom.todolist.configuration.models;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TestingUser {

    // Class variables:
    private final User user; // User object;
    private final Account account; // User account;
    public static final String DEFAULT_ACCOUNT_PASSWORD = "1!aAbBcCdD"; // DEFAULT ACCOUNT PASSWORD;

    private TestingUser() {
        this.user = new User();
        this.account = new Account();
    }

    public TestingUser(Account anAccount, User aUser) {
        this.user = aUser;
        this.account = anAccount;

        this.account.setUserOwner(this.user);
        this.user.setUserAccount(this.account);
    }

    @Override
    public String toString() {
        return String.format("TestingUser[User: %s, Account: %s]", this.user, this.account);
    }

    public static class Builder {

        // Class variables:
        private String userName;
        private String accountEmail;
        private String accountPassword;

        public Builder setUserName(String aUserName) {
            this.userName = aUserName;
            return this;
        }

        public Builder setAccountEmail(String anAccountEmail) {
            this.accountEmail = anAccountEmail;
            return this;
        }

        public Builder setAccountPassword(String anAccountPassword) {
            this.accountPassword = anAccountPassword;
            return this;
        }

        public TestingUser build() {
            TestingUser testingUser = new TestingUser();
            testingUser.getUser().setName(this.userName);
            testingUser.getAccount().setEmail(this.accountEmail);
            testingUser.getAccount().setPassword(Objects.requireNonNullElse(this.accountPassword, DEFAULT_ACCOUNT_PASSWORD));

            return testingUser;
        }
    }
}
