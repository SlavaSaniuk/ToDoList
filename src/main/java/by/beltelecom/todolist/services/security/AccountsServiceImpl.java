package by.beltelecom.todolist.services.security;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.repositories.AccountsRepository;
import by.beltelecom.todolist.exceptions.RuntimeNotFoundException;
import by.beltelecom.todolist.utilities.logging.Checks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

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
        if (founded.isEmpty()) throw new RuntimeNotFoundException(Account.class);
        return founded.get();
    }

    @Override
    @Transactional
    public Account saveAccount(Account anAccount) {
        LOGGER.debug("Try to save [Account] entity in database.");

        // Check parameters:
        Objects.requireNonNull(anAccount, Checks.argumentNotNull("anAccount", Account.class));
        Objects.requireNonNull(anAccount.getUserOwner(), Checks.propertyOfArgumentNotNull("userAccount", "anAccount", Account.class));
        Objects.requireNonNull(anAccount.getEmail(), Checks.propertyOfArgumentNotNull("email", "anAccount", Account.class));
        Objects.requireNonNull(anAccount.getPassword(), Checks.propertyOfArgumentNotNull("password", "anAccount", Account.class));
        if (anAccount.getEmail().length() == 0)
            throw new IllegalArgumentException(Checks.Strings.stringNotEmpty("Email"));
        if (anAccount.getPassword().length() == 0)
            throw new IllegalArgumentException(Checks.Strings.stringNotEmpty("Password"));

        // Save account in database:
        return this.accountsRepository.save(anAccount);
    }
}
