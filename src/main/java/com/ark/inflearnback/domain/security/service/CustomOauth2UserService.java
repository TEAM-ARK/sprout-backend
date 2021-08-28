package com.ark.inflearnback.domain.security.service;

import com.ark.inflearnback.domain.security.model.Member;
import com.ark.inflearnback.domain.security.model.OAuthAttributes;
import com.ark.inflearnback.domain.security.model.Role;
import com.ark.inflearnback.domain.security.model.SessionMember;
import com.ark.inflearnback.domain.security.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                                                  .getProviderDetails()
                                                  .getUserInfoEndpoint()
                                                  .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        httpSession.setAttribute("user", new SessionMember(save(attributes)));
        Role role = Role.of("ROLE_USER", "사용자", false);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(role.getAuthority())),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private Member save(OAuthAttributes attributes){
        return memberRepository.save(memberRepository.findByEmail(attributes.getEmail())
                                                     .orElse(attributes.toEntity()));
    }
}
