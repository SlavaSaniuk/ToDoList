package by.beltelecom.todolist.integration.serviecs.security.authentication;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.exceptions.PasswordNotValidException;
import by.beltelecom.todolist.security.SecurityConfiguration;
import by.beltelecom.todolist.security.authentication.CredentialsValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@EnableConfigurationProperties(SecurityProperties.class)
@Import(SecurityConfiguration.class)
public class CredentialsValidatorTestsCase {

    @Autowired
    private CredentialsValidator credentialsValidator;

    @Test
    void credentialsValidator_propertiesFromApplicationProperties_shouldUserDefinedValues() {
        Assertions.assertNotNull(credentialsValidator);

        Assertions.assertEquals(10, credentialsValidator.validationRules().get("to.do.security.passwords.min-length"));
        Assertions.assertTrue((Boolean) this.credentialsValidator.validationRules().get("to.do.security.passwords.use-numbers"));
        Assertions.assertTrue((Boolean) this.credentialsValidator.validationRules().get("to.do.security.passwords.use-uppercase-letter"));
        Assertions.assertTrue((Boolean) this.credentialsValidator.validationRules().get("to.do.security.passwords.use-special-symbols"));

    }

    @Test
    void validatePassword_lengthIsInvalid_shouldThrowPNVE() {
        String password = "A!1dff";
        Assertions.assertThrows(PasswordNotValidException.class, () -> this.credentialsValidator.validatePassword(password));
    }

    @Test
    void validatePassword_hasNotNumber_shouldThrowPNVE() {
        String password = "A!asdgfddfdff";
        Assertions.assertThrows(PasswordNotValidException.class, () -> this.credentialsValidator.validatePassword(password));
    }

    @Test
    void validatePassword_hasNotUppercaseLetter_shouldThrowPNVE() {
        String password = "a!asdgfddfdff45";
        Assertions.assertThrows(PasswordNotValidException.class, () -> this.credentialsValidator.validatePassword(password));
    }

    @Test
    void validatePassword_hasNotSpecialSymbols_shouldThrowPNVE() {
        String password = "a45Asdgfddfdff";
        Assertions.assertThrows(PasswordNotValidException.class, () -> this.credentialsValidator.validatePassword(password));
    }

    @Test
    void validatePassword_validPassword_shouldReturnTrue() {
        String password = "!1Abcderdsdasd";
        try {
            Assertions.assertTrue(this.credentialsValidator.validatePassword(password));
        } catch (PasswordNotValidException e) {
            Assertions.fail();
        }
    }
}
