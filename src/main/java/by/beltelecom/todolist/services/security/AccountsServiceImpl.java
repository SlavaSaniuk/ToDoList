package by.beltelecom.todolist.services.security;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.repositories.AccountsRepository;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.utilities.logging.Checks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of {@link AccountsService} service bean.
 */
public class AccountsServiceImpl implements AccountsService {

    private final AccountsRepository accountsRepository; // Service bean (Autowired via constructor);
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsServiceImpl.class);

    public AccountsServiceImpl(AccountsRepository anAccountsRepository) {
        // Maps parameters:
        this.accountsRepository = anAccountsRepository;
    }

    @Override
    public Optional<Account> getAccountOptByEmail(String aEmail) {
        LOGGER.debug("Try to get [Account] entity from database by [Email] parameter.");

        // Check parameter:
        Objects.requireNonNull(aEmail, Checks.argumentNotNull("aEmail", String.class));
        if (aEmail.isEmpty()) throw new IllegalArgumentException(Checks.Strings.stringNotEmpty("aEmail"));

        // Find account in database:
        return this.accountsRepository.findByEmail(aEmail);
    }

    @Override
    public Account getAccountByEmail(String aEmail) {
        Optional<Account> founded = this.getAccountOptByEmail(aEmail);
        if (founded.isEmpty()) throw new NotFoundException(Account.class);
        return founded.get();
    }
}
