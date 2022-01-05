package team.ark.sprout.adapter.out.persistence;

import org.springframework.stereotype.Component;
import team.ark.sprout.domain.account.Account;
import team.ark.sprout.domain.account.AccountDetails;
import team.ark.sprout.domain.account.Alarm;

@Component
public class AccountMapper {
    public Account mapToDomain(AccountEntity entity) {
        return Account.builder()
            .id(entity.getId())
            .details(
                AccountDetails.builder()
                    .username(entity.getUsername())
                    .profileImage(entity.getProfileImage())
                    .siteUrl(entity.getSiteUrl())
                    .location(entity.getLocation())
                    .email(entity.getEmail())
                    .bio(entity.getBio())
                    .build()
            )
            .alarm(
                Alarm.builder()
                    .studyCreatedByWeb(entity.isStudyCreatedByWeb())
                    .studyEnrollmentResultByWeb(entity.isStudyEnrollmentResultByWeb())
                    .studyUpdatedByWeb(entity.isStudyUpdatedByWeb())
                    .build()
            )
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();
    }

    public AccountEntity mapToEntity(Account account) {
        return AccountEntity.builder()
            .id(account.getId())
            .username(account.getUsername())
            .profileImage(account.getUsername())
            .siteUrl(account.getDetails().getSiteUrl())
            .location(account.getDetails().getLocation())
            .email(account.getDetails().getEmail())
            .bio(account.getDetails().getBio())
            .studyCreatedByWeb(account.getAlarm().isStudyCreatedByWeb())
            .studyEnrollmentResultByWeb(account.getAlarm().isStudyEnrollmentResultByWeb())
            .studyUpdatedByWeb(account.getAlarm().isStudyUpdatedByWeb())
            .build();
    }
}
