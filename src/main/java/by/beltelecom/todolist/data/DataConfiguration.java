package by.beltelecom.todolist.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataConfiguration {

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().type(MysqlDataSource.class)
                .url("jdbc:mysql://localhost:3306/todolist")
                .username("administrator")
                .password("1234").build();
    }
}
