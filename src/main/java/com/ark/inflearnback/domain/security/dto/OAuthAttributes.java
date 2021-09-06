package com.ark.inflearnback.domain.security.dto;

import com.ark.inflearnback.domain.security.exception.Oauth2UserNotVerifiedException;
import com.ark.inflearnback.domain.security.type.RoleType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.Objects;

@Getter
public class OAuthAttributes {
    private final OAuth2User oAuth2User;
    private final String registrationId;
    private final String email;
    private final String userNameAttributeName;

    @Builder
    private OAuthAttributes(final OAuth2User oAuth2User, final String registrationId, final String email, final String userNameAttributeName) {
        this.oAuth2User = oAuth2User;
        this.registrationId = registrationId;
        this.email = email;
        this.userNameAttributeName = userNameAttributeName;
    }

    public static OAuthAttributes of(final OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if ("github".equals(registrationId)) {
            return ofGithub(oAuth2User, userRequest);
        }

        if ("facebook".equals(registrationId)) {
            return ofFacebook(oAuth2User, userRequest);
        }

        return ofGoogle(oAuth2User, userRequest);
    }

    private static OAuthAttributes ofGoogle(final OAuth2User oAuth2User, final OAuth2UserRequest userRequest) {
        return OAuthAttributes.builder()
                .registrationId(userRequest.getClientRegistration().getRegistrationId())
                .email(oAuth2User.getAttribute("email"))
                .userNameAttributeName(userRequest.getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName())
                .build();
    }

    private static OAuthAttributes ofGithub(final OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
        return OAuthAttributes.builder()
                .oAuth2User(oAuth2User)
                .registrationId(userRequest.getClientRegistration().getRegistrationId())
                .email(oAuth2User.getAttribute("email"))
                .userNameAttributeName(userRequest.getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName())
                .build();
    }

    private static OAuthAttributes ofFacebook(final OAuth2User oAuth2User, final OAuth2UserRequest userRequest) {
        if(Objects.isNull(oAuth2User.getAttribute("email"))) {
            throw new OAuth2AuthenticationException(new OAuth2Error("email_not_exist", "user_email_is_null!!!!", null));
        }

        return OAuthAttributes.builder()
                .registrationId(userRequest.getClientRegistration().getRegistrationId())
                .email(oAuth2User.getAttribute("email"))
                .userNameAttributeName(userRequest.getClientRegistration()
                        .getProviderDetails()
                        .getUserInfoEndpoint()
                        .getUserNameAttributeName())
                .build();
    }

    public DefaultOAuth2User customDefaultOauth2User() {
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(RoleType.MEMBER.get())),
                oAuth2User.getAttributes(), this.userNameAttributeName);
    }
}
