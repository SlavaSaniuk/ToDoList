package by.beltelecom.todolist.integration.security.rest.jwt;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenService;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(SecurityProperties.class)
@ContextConfiguration(classes = {JsonWebTokenServiceImpl.class, SecurityProperties.Jwt.class})
public class JsonWebTokenServiceTestsCase {
    private final String email1 = "123@mail.com";
    private final String email2 = "456@mail.com";

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebTokenServiceTestsCase.class);
    @Autowired
    private JsonWebTokenService jsonWebTokenService;

    @Test
    void generateToken_anyEmail_shouldGenerateJwtTokens() {

        String jwt1 = this.jsonWebTokenService.generateToken(this.email1);
        String jwt2 = this.jsonWebTokenService.generateToken(this.email2);

        Assertions.assertNotNull(jwt1);
        Assertions.assertFalse(jwt1.isEmpty());
        LOGGER.debug("JWT1: " +jwt1);

        Assertions.assertNotNull(jwt2);
        Assertions.assertFalse(jwt2.isEmpty());
        LOGGER.debug("JWT2: " +jwt2);
    }

    @Test
    void verifyToken_generatedTokens_shouldReturnEmails() {

        String jwt1 = this.jsonWebTokenService.generateToken(this.email1);
        String jwt2 = this.jsonWebTokenService.generateToken(this.email2);

        String actualEmail1 = this.jsonWebTokenService.verifyToken(jwt1);
        String actualEmail2 = this.jsonWebTokenService.verifyToken(jwt2);

        Assertions.assertNotNull(jwt1);
        Assertions.assertEquals(this.email1, actualEmail1);
        LOGGER.debug("Email 1: " +this.email1);

        Assertions.assertNotNull(jwt2);
        Assertions.assertEquals(this.email2, actualEmail2);
        LOGGER.debug("Email 2: " +this.email2);
    }
}
