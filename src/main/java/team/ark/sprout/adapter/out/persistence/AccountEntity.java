package team.ark.sprout.adapter.out.persistence;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;
import team.ark.sprout.common.config.extension.ExtensionTimesEntity;

@Entity
@Getter
@Table(name = "ACCOUNT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEntity extends ExtensionTimesEntity {
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    @Nationalized
    @Column(nullable = false, length = 50)
    private String password;

    private String biography;

    private String siteUrl;

    private String occupation;

    private String location;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean emailVerified;
    private boolean studyCreatedByWeb;
    private boolean studyEnrollmentResultByWeb;
    private boolean studyUpdatedByWeb;

    @Builder
    private AccountEntity(Long id, String email, String nickname, String password, String biography, String siteUrl, String occupation,
        String location, String profileImage, boolean emailVerified, boolean studyCreatedByWeb, boolean studyEnrollmentResultByWeb, boolean studyUpdatedByWeb) {
        super.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.biography = biography;
        this.siteUrl = siteUrl;
        this.occupation = occupation;
        this.location = location;
        this.profileImage = profileImage;
        this.emailVerified = emailVerified;
        this.studyCreatedByWeb = studyCreatedByWeb;
        this.studyEnrollmentResultByWeb = studyEnrollmentResultByWeb;
        this.studyUpdatedByWeb = studyUpdatedByWeb;
    }
}
