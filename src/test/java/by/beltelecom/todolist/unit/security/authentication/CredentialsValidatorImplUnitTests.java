package by.beltelecom.todolist.unit.security.authentication;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.security.authentication.CredentialsValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CredentialsValidatorImplUnitTests {

    private CredentialsValidatorImpl credentialsValidator;

    @MockBean
    private SecurityProperties securityProperties;

    @BeforeEach
    void beforeEach() {
        SecurityProperties.Passwords passwords = new SecurityProperties.Passwords();
        passwords.setMinLength(1);
        passwords.setUseNumbers(true);
        passwords.setUseSpecialSymbols(true);
        passwords.setUseUppercaseLetter(true);
        Mockito.when(this.securityProperties.getPasswords()).thenReturn(passwords);

        this.credentialsValidator = new CredentialsValidatorImpl(securityProperties);
    }

    @Test
    void passwordHasMinimumLength_hasNotMinLength_shouldReturnFalse() {
        this.credentialsValidator.setPasswordMinLength(8);
        String password = "1234567";

        Assertions.assertFalse(this.credentialsValidator.passwordHasMinimumLength(password));
    }

    @Test
    void passwordHasMinimumLength_hasMinLength_shouldReturnFalse() {
        this.credentialsValidator.setPasswordMinLength(8);
        String password = "12345678";
        String password1 = "123456789";

        Assertions.assertTrue(this.credentialsValidator.passwordHasMinimumLength(password));
        Assertions.assertTrue(this.credentialsValidator.passwordHasMinimumLength(password1));
    }

        @Test
    void isContainNumbers_passNotContainAnyNumbers_shouldReturnFalse() {
        String password = "ABCDEFGH";
        Assertions.assertFalse(this.credentialsValidator.isContainNumbers(password));
    }

    @Test
    void isContainNumbers_passwordContainPassword_shouldReturnTrue() {
        String password1 = "1ABCDEFGH";
        String password2 = "ABCD32EFGH";
        String password3 = "ABCDEFGH64";

        Assertions.assertTrue(this.credentialsValidator.isContainNumbers(password1));
        Assertions.assertTrue(this.credentialsValidator.isContainNumbers(password2));
        Assertions.assertTrue(this.credentialsValidator.isContainNumbers(password3));
    }

    @Test
    void isContainsUppercaseLetters_notContain_shouldReturnFalse() {
        String password = "password";

        Assertions.assertFalse(this.credentialsValidator.isContainUppercaseLetters(password));
    }

    @Test
    void isContainsUppercaseLetters_containUppercaseLetters_shouldReturnTrue() {
        String password1 = "Password";
        String password2 = "pasSword";
        String password3 = "passworD";

        Assertions.assertTrue(this.credentialsValidator.isContainUppercaseLetters(password1));
        Assertions.assertTrue(this.credentialsValidator.isContainUppercaseLetters(password2));
        Assertions.assertTrue(this.credentialsValidator.isContainUppercaseLetters(password3));
    }

    @Test
    void isContainSpecialSymbols_notContain_shouldReturnFalse() {
        String password = "password";

        Assertions.assertFalse(this.credentialsValidator.isContainSpecialSymbols(password));
    }

    @Test
    void isContainSpecialSymbols_containSpecialSymbols_shouldReturnTruee() {
        String password1 = "!Password";
        String password2 = "pas.Sword";
        String password3 = "passworD%";

        Assertions.assertTrue(this.credentialsValidator.isContainSpecialSymbols(password1));
        Assertions.assertTrue(this.credentialsValidator.isContainSpecialSymbols(password2));
        Assertions.assertTrue(this.credentialsValidator.isContainSpecialSymbols(password3));
    }
}
