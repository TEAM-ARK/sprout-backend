package team.ark.sprout.domain.account;

import lombok.Builder;
import lombok.Value;
import team.ark.sprout.port.in.AccountExtractor;

@Value
@Builder
public class AccountDetails {
    String username;
    String profileImage;
    String siteUrl;
    String location;
    String email;
    String bio;

    public static AccountDetails from(AccountExtractor extractor) {
        return AccountDetails.builder()
            .username(extractor.getUsername())
            .profileImage(extractor.getProfileImage())
            .siteUrl(extractor.getSiteUrl())
            .location(extractor.getLocation())
            .email(extractor.getEmail())
            .bio(extractor.getBio())
            .build();
    }

    public AccountDetails update(AccountExtractor extractor) {
        return AccountDetails.builder()
            .username(compare(username, extractor.getUsername()))
            .profileImage(compare(profileImage, extractor.getProfileImage()))
            .siteUrl(compare(siteUrl, extractor.getSiteUrl()))
            .location(compare(location, extractor.getLocation()))
            .email(compare(email, extractor.getEmail()))
            .bio(compare(bio, extractor.getBio()))
            .build();
    }

    private String compare(String beforeValue, String afterValue) {
        if (beforeValue == null) {
            return afterValue;
        }
        if (beforeValue.equals(afterValue)) {
            return beforeValue;
        }
        return afterValue;
    }
}
