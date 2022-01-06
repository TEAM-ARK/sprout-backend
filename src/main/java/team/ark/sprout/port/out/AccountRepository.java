package team.ark.sprout.port.out;

import java.util.Optional;
import team.ark.sprout.domain.account.Account;

public interface AccountRepository {
    Account save(Account account);

    Optional<Account> findByUsername(String username);
}
