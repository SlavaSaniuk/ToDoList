package by.beltelecom.todolist;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.security.SecurityConfiguration;
import by.beltelecom.todolist.services.ServicesConfiguration;
import by.beltelecom.todolist.web.WebmvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, excludeName = {"by.beltelecom.todolist.services.*",
        "by.beltelecom.todolist.security.*"})
@EnableConfigurationProperties(SecurityProperties.class)
@Import({ServicesConfiguration.class, WebmvcConfiguration.class, SecurityConfiguration.class})
public class ToDoListApplication {

    public static void main(String[] args) {

        SpringApplication.run(ToDoListApplication.class, args);
    }

}
