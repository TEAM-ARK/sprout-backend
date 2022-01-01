package team.ark.sprout.domain.account;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "id")
@Builder(access = AccessLevel.PRIVATE)
public class Account {
    Long id;
    AccountBasic basic;
    AccountDetails details;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    private Account(Long id, AccountBasic basic, AccountDetails details, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.basic = basic;
        this.details = details;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Account create(AccountBasic basic, AccountDetails details) {
        return Account.builder()
            .id(null)
            .basic(basic)
            .details(details)
            .createdAt(null)
            .updatedAt(null)
            .build();
    }

    public static Account from(Long id, AccountBasic basic, AccountDetails details, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return Account.builder()
            .id(id)
            .basic(basic)
            .details(details)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();
    }

    public Account update(Account account) {
        return Account.builder()
            .id(account.getId())
            .basic(account.getBasic())
            .details(account.getDetails())
            .createdAt(account.getCreatedAt())
            .updatedAt(account.getUpdatedAt())
            .build();
    }
}
