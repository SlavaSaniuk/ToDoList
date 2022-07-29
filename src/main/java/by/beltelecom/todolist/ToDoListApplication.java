package by.beltelecom.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ToDoListApplication {

    public static void main(String[] args) {

        SpringApplication.run(ToDoListApplication.class, args);
    }

}
