package team.ark.sprout.port.out;

import team.ark.sprout.domain.account.Account;

public interface AccountRepository {
    Account save(Account account);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickname);
}