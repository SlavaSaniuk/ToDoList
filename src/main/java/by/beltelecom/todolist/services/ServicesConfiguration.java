package by.beltelecom.todolist.services;

import by.beltelecom.todolist.data.repositories.AccountsRepository;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.data.repositories.UsersRepository;
import by.beltelecom.todolist.services.security.*;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.services.tasks.TasksServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    private TasksRepository tasksRepository;
    private UsersRepository usersRepository;

    private AccountsRepository accountsRepository;

    @Bean("tasksService")
    public TasksService tasksService() {
        return new TasksServiceImpl(this.tasksRepository);

    }
    @Bean("usersService")
    public UsersService usersService() {
        return new UsersServiceImpl(this.usersRepository);
    }
    @Bean("accountsService")
    public AccountsService accountsService() {
        return new AccountsServiceImpl(this.accountsRepository);
    }

    @Autowired
    public void setTasksRepository(TasksRepository aTaskRepository) {
        LOGGER.debug("Autowire {} repository bean in {} configuration.", TasksRepository.class,
                ServicesConfiguration.class);
        this.tasksRepository = aTaskRepository;
    }
    @Autowired
    public void setUsersRepository(UsersRepository aUsersRepository) {
        LOGGER.debug("Autowire {} repository bean in {} configuration.", UsersRepository.class,
                ServicesConfiguration.class);
        this.usersRepository = aUsersRepository;
    }

    @Autowired
    public void setAccountsRepository(AccountsRepository anAccountsRepository) {
        LOGGER.debug("Autowire {} repository bean in {} configuration.", AccountsRepository.class,
                ServicesConfiguration.class);
        this.accountsRepository = anAccountsRepository;
    }
}
