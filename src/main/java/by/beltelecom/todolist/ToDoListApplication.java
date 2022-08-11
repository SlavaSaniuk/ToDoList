package by.beltelecom.todolist;

import by.beltelecom.todolist.services.ServicesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, excludeName = {"by.beltelecom.todolist.services.*"})
@Import({ServicesConfiguration.class})
public class ToDoListApplication {

    public static void main(String[] args) {

        SpringApplication.run(ToDoListApplication.class, args);
    }

}
