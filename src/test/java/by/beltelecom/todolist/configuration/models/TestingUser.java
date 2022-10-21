package by.beltelecom.todolist.configuration.models;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import lombok.Getter;

import java.util.Objects;

public class TestingUser {

    // Class variables:
    @Getter
    private final User user; // User object;
    @Getter
    private final Account account; // User account;
    private final String jsonWebToken; // JWT;
    private final Authentication authentication = new Authentication(); // Authentication inner class;

    public static final String DEFAULT_ACCOUNT_PASSWORD = "1!aAbBcCdD"; // DEFAULT ACCOUNT PASSWORD;

    private TestingUser() {
        this.user = new User();
        this.account = new Account();
        this.jsonWebToken = null;
    }

    public TestingUser(Account anAccount, User aUser, String aJwt) {
        this.user = aUser;
        this.account = anAccount;
        this.jsonWebToken = aJwt;

        this.account.setUserOwner(this.user);
        this.user.setUserAccount(this.account);
    }

    @Override
    public String toString() {
        return String.format("TestingUser[User: %s, Account: %s]", this.user, this.account);
    }

    public Authentication authentication() {
        return this.authentication;
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

    public class Authentication {
        public String getJwt() {
            return jsonWebToken;
        }

        public String getAuthorizationHeaderValue() {
            return "Beaver " +jsonWebToken;
        }
    }
}
