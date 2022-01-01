package team.ark.sprout.adapter.out.persistence;

import org.springframework.stereotype.Component;
import team.ark.sprout.domain.account.Account;
import team.ark.sprout.domain.account.AccountBasic;
import team.ark.sprout.domain.account.AccountDetails;
import team.ark.sprout.domain.account.Flags;

@Component
class AccountMapper {
    Account mapToDomain(AccountEntity entity) {
        return Account.from(
            entity.getId(),
            AccountBasic.of(
                entity.getEmail(),
                entity.getNickname(),
                entity.getPassword()
            ),
            AccountDetails.of(
                entity.getBiography(),
                entity.getSiteUrl(),
                entity.getOccupation(),
                entity.getLocation(),
                entity.getProfileImage(),
                Flags.of(
                    entity.isEmailVerified(),
                    entity.isStudyCreatedByWeb(),
                    entity.isStudyEnrollmentResultByWeb(),
                    entity.isStudyUpdatedByWeb()
                )
            ),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    AccountEntity mapToJpaEntity(Account account) {
        AccountBasic basic = account.getBasic();
        AccountDetails details = account.getDetails();
        Flags flags = details.getFlags();
        return AccountEntity.builder()
            .id(account.getId())
            .email(basic.getEmail())
            .nickname(basic.getNickname())
            .password(basic.getPassword())
            .biography(details.getBiography())
            .siteUrl(details.getSiteUrl())
            .occupation(details.getOccupation())
            .location(details.getLocation())
            .profileImage(details.getProfileImage())
            .emailVerified(flags.isEmailVerified())
            .studyCreatedByWeb(flags.isStudyCreatedByWeb())
            .studyEnrollmentResultByWeb(flags.isStudyEnrollmentResultByWeb())
            .studyUpdatedByWeb(flags.isStudyUpdatedByWeb())
            .build();
    }
}
