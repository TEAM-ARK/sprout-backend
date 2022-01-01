package team.ark.sprout.domain.account;

import lombok.Value;

@Value(staticConstructor = "of")
public class Flags {
    boolean emailVerified;
    boolean studyCreatedByWeb;
    boolean studyEnrollmentResultByWeb;
    boolean studyUpdatedByWeb;
}
