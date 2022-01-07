package team.ark.sprout.domain.account;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import team.ark.sprout.port.account.in.AccountExtractor;

@Value
@Builder
@EqualsAndHashCode(of = "id")
public class Account {
    Long id;
    AccountDetails details;
    Alarm alarm;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static Account from(AccountExtractor extractor) {
        return Account.builder()
            .details(AccountDetails.from(extractor))
            .alarm(Alarm.create())
            .build();
    }

    public Account update(AccountExtractor extractor) {
        return Account.builder()
            .id(id)
            .details(details.update(extractor))
            .alarm(alarm)
            .build();
    }

    public String getUsername() {
        return details.getUsername();
    }
}
