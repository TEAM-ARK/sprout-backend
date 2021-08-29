package com.ark.inflearnback.domain.security.service;

import com.ark.inflearnback.domain.security.dto.OAuthAttributes;
import com.ark.inflearnback.domain.security.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final HttpSession httpSession;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Oauth2 User Login Service....");
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(delegate.loadUser(userRequest), userRequest);
        log.info("Oauth2 User Login Success!!!");
        log.info("################################");
        log.info("HttpSession adding..");
        //FIXME: 이슈에 올려둔 내용 user로 해도 괜찮을지
        httpSession.setAttribute("user", new SessionMember(oAuthAttributes.getEmail()));
        log.info("HttpSession Success!!!");

        return oAuthAttributes.customDefaultOauth2User();
    }
}
