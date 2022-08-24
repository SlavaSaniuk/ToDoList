package by.beltelecom.todolist.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataConfiguration {

    @Bean(name = "dataSource")
    @Profile("production")
    public DataSource productionDataSource() {
        return DataSourceBuilder.create().type(MysqlDataSource.class)
                .url("jdbc:mysql://localhost:3306/todolist")
                .username("administrator")
                .password("1234").build();
    }

    @Bean(name = "dataSource")
    @Profile("development")
    public DataSource developmentDataSource() {
        return DataSourceBuilder.create().type(MysqlDataSource.class)
                .url("jdbc:mysql://localhost:3306/todotest")
                .username("administrator")
                .password("1234").build();
    }
}
