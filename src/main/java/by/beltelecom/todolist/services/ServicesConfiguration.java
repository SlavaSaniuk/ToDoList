package by.beltelecom.todolist.services;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.repositories.AccountsRepository;
import by.beltelecom.todolist.data.repositories.TasksRepository;
import by.beltelecom.todolist.data.repositories.UsersRepository;
import by.beltelecom.todolist.services.security.*;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.services.tasks.TasksServiceImpl;
import by.beltelecom.todolist.services.users.UsersService;
import by.beltelecom.todolist.services.users.UsersServiceImpl;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);
    private TasksRepository tasksRepository;
    private UsersRepository usersRepository; // Spring repository bean (Autowired via setter);
    private AccountsRepository accountsRepository;

    /**
     * Construct new application services configuration bean.
     */
    public ServicesConfiguration() {
        LOGGER.debug(SpringLogging.Creation.createBean(ServicesConfiguration.class));
    }

    /**
     * Interface for work with {@link Task} objects in application.
     */
    @Bean("tasksService")
    public TasksService tasksService() {
        LOGGER.debug(SpringLogging.Creation.createBean(TasksService.class));
        return new TasksServiceImpl(this.tasksRepository);
    }

    /**
     * UsersService service bean used to manipulate {@link by.beltelecom.todolist.data.models.User} entities objects
     * in application;
     * @return - {@link UsersService} service bean/
     */
    @Bean("usersService")
    public UsersService usersService() {
        LOGGER.debug(SpringLogging.Creation.createBean(UsersService.class));
        return new UsersServiceImpl(this.usersRepository);
    }

    @Bean("accountsService")
    public AccountsService accountsService() {
        return new AccountsServiceImpl(this.accountsRepository);
    }

    /**
     * Autowire {@link TasksRepository} repository bean.
     * @param aTaskRepository - repository bean.
     */
    @Autowired
    public void setTasksRepository(TasksRepository aTaskRepository) {
        LOGGER.debug(SpringLogging.Autowiring
                .autowireInConfiguration(TasksRepository.class, ServicesConfiguration.class));
        this.tasksRepository = aTaskRepository;
    }

    /**
     * Autowire {@link UsersRepository} repository bean.
     * @param aUsersRepository - repository bean.
     */
    @Autowired
    public void setUsersRepository(UsersRepository aUsersRepository) {
        LOGGER.debug(SpringLogging.Autowiring.autowireInConfiguration(UsersRepository.class, ServicesConfiguration.class));
        this.usersRepository = aUsersRepository;
    }

    @Autowired
    public void setAccountsRepository(AccountsRepository anAccountsRepository) {
        LOGGER.debug("Autowire {} repository bean in {} configuration.", AccountsRepository.class,
                ServicesConfiguration.class);
        this.accountsRepository = anAccountsRepository;
    }
}
