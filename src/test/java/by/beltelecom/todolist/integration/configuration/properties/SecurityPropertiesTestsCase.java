package by.beltelecom.todolist.integration.configuration.properties;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityPropertiesTestsCase {

    @Autowired
    private SecurityProperties securityProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityPropertiesTestsCase.class);

    @Test
    void getHelloString_helloStringIsSet_shouldReturnHelloString() {
        Assertions.assertNotNull(securityProperties);
        Assertions.assertNotNull(securityProperties.getHelloString());

        LOGGER.warn("Hello world: " +securityProperties.getHelloString());
    }
}
