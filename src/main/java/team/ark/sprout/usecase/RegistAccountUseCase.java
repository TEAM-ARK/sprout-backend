package team.ark.sprout.usecase;

import lombok.RequiredArgsConstructor;
import team.ark.sprout.adapter.out.persistence.AccountRepository;
import team.ark.sprout.common.UseCase;
import team.ark.sprout.domain.account.Account;
import team.ark.sprout.port.in.RegistAccount;

@UseCase
@RequiredArgsConstructor
class RegistAccountUseCase implements RegistAccount {
    private final AccountRepository accountRepository;

    @Override
    public Account regist(Account account) {
        return accountRepository.save(account);
    }
}
