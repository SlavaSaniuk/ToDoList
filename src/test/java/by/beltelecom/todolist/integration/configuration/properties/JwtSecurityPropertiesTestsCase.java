package by.beltelecom.todolist.integration.configuration.properties;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(SecurityProperties.class)
@TestPropertySource("classpath:application.properties")
public class JwtSecurityPropertiesTestsCase {

    @Autowired
    private SecurityProperties securityProperties;

    private SecurityProperties.Jwt jwtSecurityProperties;

    @BeforeEach
    void beforeEach() {
        Assertions.assertNotNull(this.securityProperties);
        this.jwtSecurityProperties = this.securityProperties.getJwt();
        Assertions.assertNotNull(this.jwtSecurityProperties);
    }

    @AfterEach
    void afterEach() {
        this.jwtSecurityProperties = null;
    }

    @Test
    void getSecretKey_secretKeyIsSet_shouldReturnSecretKey() {
        String expectedSecretKey = "AaBbCcDd";
        Assertions.assertNotNull(this.jwtSecurityProperties.getSecretKey());
        Assertions.assertEquals(expectedSecretKey, this.jwtSecurityProperties.getSecretKey());
    }

    @Test
    void getSubject_subjectPropertyIsEmpty_shouldUseEmptyValue() {
        Assertions.assertTrue(this.jwtSecurityProperties.getSubject().isEmpty());
    }

    @Test
    void getIssuer_propertyNotSet_shouldUseDefaultValue() {
        Assertions.assertNotNull(this.jwtSecurityProperties.getIssuer());
        Assertions.assertEquals(SecurityProperties.Jwt.DEFAULT_ISSUER, this.jwtSecurityProperties.getIssuer());
    }

}
