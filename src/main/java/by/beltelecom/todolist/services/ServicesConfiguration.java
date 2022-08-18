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
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServicesConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    private TasksRepository tasksRepository;
    private UsersRepository usersRepository;

    private AccountsRepository accountsRepository;

    private PasswordEncoder passwordEncoder;

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
    @Bean("signService")
    public SignService signService() {
        LOGGER.debug("Create {} service bean", SignService.class);
        return new SignServiceImpl(this.accountsRepository, this.passwordEncoder, this.usersService());
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
    @Autowired
    public void setPasswordEncoder(PasswordEncoder aPasswordEncoder) {
        LOGGER.debug("Autowire {} repository bean in {} configuration.", PasswordEncoder.class,
                ServicesConfiguration.class);
        this.passwordEncoder = aPasswordEncoder;
    }
}
