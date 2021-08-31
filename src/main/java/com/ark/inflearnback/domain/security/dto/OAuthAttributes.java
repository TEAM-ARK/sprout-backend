package com.ark.inflearnback.domain.security.dto;

import com.ark.inflearnback.domain.security.exception.Oauth2UserNotVerifiedException;
import com.ark.inflearnback.domain.security.type.RoleType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;

@Getter
public class OAuthAttributes {
    private final OAuth2User oAuth2User;
    private final String registrationId;
    private final String userNameAttributeName;

    @Builder
    private OAuthAttributes(final OAuth2User oAuth2User, final String registrationId, final String userNameAttributeName) {
        this.oAuth2User = oAuth2User;
        this.registrationId = registrationId;
        this.userNameAttributeName = userNameAttributeName;
    }

    public static OAuthAttributes of(final OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
        boolean isEmailVerified = oAuth2User.getAttribute("email_verified");

        if (!isEmailVerified) {
            throw new Oauth2UserNotVerifiedException("인증되지 않은 사용자입니다.");
        }

        return OAuthAttributes.builder()
                .oAuth2User(oAuth2User)
                .registrationId(userRequest.getClientRegistration().getRegistrationId())
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

    public String getSub() {
        return oAuth2User.getAttribute("sub");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public boolean isEmailVerified() {
        return oAuth2User.getAttribute("email_verified");
    }
}
