package com.ark.inflearnback.configuration.security.handler;

import com.ark.inflearnback.configuration.http.model.form.HttpResponse;
import com.ark.inflearnback.configuration.security.model.entity.Member;
import com.ark.inflearnback.configuration.security.model.entity.Role;
import com.ark.inflearnback.configuration.security.repository.MemberRepository;
import com.ark.inflearnback.configuration.security.repository.RoleRepository;
import com.ark.inflearnback.configuration.security.type.RoleType;
import com.ark.inflearnback.domain.member.model.form.SignForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        final String socialId = getSocialId((OAuth2AuthenticationToken) authentication);
        final DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        if (Objects.isNull(oAuth2User.getAttribute("email"))) {
            final String jsonObject = responseData((OAuth2AuthenticationToken) authentication, socialId);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(302);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            objectMapper.writeValue(response.getWriter(), HttpResponse.of(HttpStatus.FOUND, jsonObject));
            return;
        }

        Optional<Member> optMember = memberRepository.findBySocialId(socialId);

        if (optMember.isEmpty()) {
            Optional<Role> optRole = roleRepository.findByRoleType(RoleType.USER);

            optRole.ifPresent(role -> {
                memberRepository.save(
                    Member.of(SignForm.of(oAuth2User.getAttribute("email"), null),
                        role,
                        socialId,
                        ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(),
                        true));
            });
        }
        emailExistCheckAndThenSignUp((OAuth2AuthenticationToken) authentication, socialId, oAuth2User);

        final RequestCache requestCache = new HttpSessionRequestCache();
        final SavedRequest savedRequest = requestCache.getRequest(request, response);
        final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        if (Objects.isNull(savedRequest)) {
            objectMapper.writeValue(response.getWriter(), HttpResponse.of(HttpStatus.OK, "Oauth2 Login Success!!"));
            return;
        }

        String redirectUrl = savedRequest.getRedirectUrl();
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }

    private String getSocialId(final OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        OAuth2User oauth2User = oAuth2AuthenticationToken.getPrincipal();

        final String registrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        if (registrationId.equals("google")) {
            return oauth2User.getAttribute("sub");
        }

        if (registrationId.equals("facebook")) {
            return oauth2User.getAttribute("id");
        }

        return Integer.toString(oauth2User.getAttribute("id"));
    }

    private String responseData(final OAuth2AuthenticationToken authentication, final String socialId) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> data = Map.of(
            "registrationId", authentication.getAuthorizedClientRegistrationId(),
            "id", socialId,
            "name", authentication.getPrincipal().getAttribute("name")
        );
        return objectMapper.writeValueAsString(HttpResponse.of(HttpStatus.FOUND, "Email is null", data));
    }

    private void emailExistCheckAndThenSignUp(OAuth2AuthenticationToken authentication, String socialId, DefaultOAuth2User oAuth2User) {
        Optional<Member> optMember = memberRepository.findBySocialId(socialId);

        if (optMember.isEmpty()) {
            Optional<Role> optRole = roleRepository.findByRoleType(RoleType.USER);

            optRole.ifPresent(role -> {
                memberRepository.save(
                    Member.of(SignForm.of(oAuth2User.getAttribute("email"), null),
                        role,
                        socialId,
                        authentication.getAuthorizedClientRegistrationId(),
                        true));
            });
        }
    }

}
