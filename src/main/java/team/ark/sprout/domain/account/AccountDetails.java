package team.ark.sprout.domain.account;

import lombok.Value;

@Value(staticConstructor = "of")
public class AccountDetails {
    String biography;
    String siteUrl;
    String occupation;
    String location;
    String profileImage;
    Flags flags;

    public static AccountDetails create() {
        Flags flags = Flags.of(false, false, false, false);
        return new AccountDetails(null, null, null, null, null, flags);
    }
}
