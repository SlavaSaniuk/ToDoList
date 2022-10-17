package by.beltelecom.todolist.configuration;

import by.beltelecom.todolist.configuration.services.TestsTasksService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServicesTestsConfiguration {

    @Bean
    public TestsTasksService testsTasksService() {
        return new TestsTasksService();
    }

}
