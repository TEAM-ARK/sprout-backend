package team.ark.sprout.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import team.ark.sprout.domain.account.Account;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountRdbmsRepository implements AccountRepository {
    private final AccountMapper accountMapper;
    private final AccountJpaRepository jpaRepository;

    @Override
    public Account save(Account account) {
        AccountEntity entity = accountMapper.mapToJpaEntity(account);
        entity = jpaRepository.save(entity);
        return accountMapper.mapToDomain(entity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByNickName(String nickname) {
        return jpaRepository.existsByNickname(nickname);
    }
}
