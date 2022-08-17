package by.beltelecom.todolist.data.repositories;

import by.beltelecom.todolist.data.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(@NonNull String aEmail);

}
