package team.ark.sprout.adapter.out.persistence;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.ark.sprout.common.config.extension.ExtensionTimesEntity;

@Entity
@Getter
@Table(name = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity extends ExtensionTimesEntity {
    @Column(unique = true)
    private String username;

    private String profileImage;

    private String siteUrl;

    private String location;

    private String email;

    private String bio;

    private boolean studyCreatedByWeb;

    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdatedByWeb;

    @Builder
    private AccountEntity(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String username, String profileImage, String siteUrl, String location, String email, String bio, boolean studyCreatedByWeb,
        boolean studyEnrollmentResultByWeb, boolean studyUpdatedByWeb) {
        super(id, createdAt, updatedAt);
        this.username = username;
        this.profileImage = profileImage;
        this.siteUrl = siteUrl;
        this.location = location;
        this.email = email;
        this.bio = bio;
        this.studyCreatedByWeb = studyCreatedByWeb;
        this.studyEnrollmentResultByWeb = studyEnrollmentResultByWeb;
        this.studyUpdatedByWeb = studyUpdatedByWeb;
    }
}
