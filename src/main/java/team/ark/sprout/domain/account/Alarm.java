package team.ark.sprout.domain.account;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Alarm {
    boolean studyCreatedByWeb;
    boolean studyEnrollmentResultByWeb;
    boolean studyUpdatedByWeb;

    public static Alarm create() {
        return new Alarm(false, false, false);
    }
}
