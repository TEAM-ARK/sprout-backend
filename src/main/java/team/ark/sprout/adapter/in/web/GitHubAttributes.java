package team.ark.sprout.adapter.in.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import team.ark.sprout.port.in.AccountExtractor;

public class GitHubAttributes implements AccountExtractor {
    @JsonProperty("login")
    private String username;

    @JsonProperty("avatar_url")
    private String profileImage;

    @JsonProperty("html_url")
    private String siteUrl;

    private String location;

    private String email;

    private String bio;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getProfileImage() {
        return profileImage;
    }

    @Override
    public String getSiteUrl() {
        return siteUrl;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getBio() {
        return bio;
    }
}
