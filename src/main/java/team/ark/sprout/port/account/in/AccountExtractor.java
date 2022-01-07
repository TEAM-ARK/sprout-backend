package team.ark.sprout.port.account.in;

public interface AccountExtractor {
    String getUsername();

    String getProfileImage();

    String getSiteUrl();

    String getLocation();

    String getEmail();

    String getBio();
}
