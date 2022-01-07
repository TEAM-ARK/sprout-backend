package team.ark.sprout.adapter.account.out.persistence;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.ark.sprout.domain.account.Account;
import team.ark.sprout.port.account.out.AccountRepository;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
class AccountRdbmsRepository implements AccountRepository {
    private final AccountMapper accountMapper;
    private final AccountJpaRepository jpaRepository;

    @Override
    @Transactional
    public Account save(Account account) {
        AccountEntity entity = accountMapper.mapToEntity(account);
        entity = jpaRepository.save(entity);
        return accountMapper.mapToDomain(entity);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
            .map(accountMapper::mapToDomain);
    }
}
