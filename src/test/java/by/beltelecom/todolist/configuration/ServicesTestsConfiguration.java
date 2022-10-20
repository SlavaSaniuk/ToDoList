package by.beltelecom.todolist.configuration;

import by.beltelecom.todolist.configuration.services.TestsTaskService;
import by.beltelecom.todolist.configuration.services.TestsTasksService;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServicesTestsConfiguration {

    @Bean
    public TestsUserService testsUserService() {
        return new TestsUserService();
    }

    @Bean
    public TestsTaskService testsTaskService() {
        return new TestsTaskService();
    }

    @Bean
    public TestsTasksService testsTasksService() {
        return new TestsTasksService();
    }

}
