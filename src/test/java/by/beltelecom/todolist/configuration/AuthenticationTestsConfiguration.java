package by.beltelecom.todolist.configuration;

import by.beltelecom.todolist.configuration.bean.TestsUsersService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AuthenticationTestsConfiguration {

    @Bean
    public TestsUsersService testsUsersService() {
        return new TestsUsersService();
    }

}
