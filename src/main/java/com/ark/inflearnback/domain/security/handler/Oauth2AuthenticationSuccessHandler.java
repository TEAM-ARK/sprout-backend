package com.ark.inflearnback.domain.security.handler;

import com.ark.inflearnback.config.model.HttpResponse;
import com.ark.inflearnback.domain.member.dto.SignRequestDto;
import com.ark.inflearnback.domain.security.model.Member;
import com.ark.inflearnback.domain.security.model.Role;
import com.ark.inflearnback.domain.security.repository.MemberRepository;
import com.ark.inflearnback.domain.security.repository.RoleRepository;
import com.ark.inflearnback.domain.security.type.RoleType;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final String DEFAULT_LOGIN_SUCCESS_URL = "/";

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        final String socialId = googleOrAnotherId((OAuth2AuthenticationToken) authentication);
        final DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

        if(Objects.isNull(oAuth2User.getAttribute("email"))) {
            String jsonObject = responseData((OAuth2AuthenticationToken) authentication, socialId);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.FOUND.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            response.getWriter().write(jsonObject);
            redirectStrategy.sendRedirect(request, response, "/signUp");
            return;
        }

        Optional<Member> optMember = memberRepository.findBySocialId(socialId);

        if(optMember.isEmpty()) {
            Optional<Role> optRole = roleRepository.findByRoleType(RoleType.USER);

            optRole.ifPresent(role -> {
                memberRepository.save(
                        Member.of(SignRequestDto.of(oAuth2User.getAttribute("email"), null),
                                role,
                                socialId,
                                ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(),
                                true));
            });
        }

        final RequestCache requestCache = new HttpSessionRequestCache();
        final SavedRequest savedRequest = requestCache.getRequest(request, response);
        final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        if(Objects.isNull(savedRequest)) {
            String defaultUrl = "/loginSuccess";
            redirectStrategy.sendRedirect(request, response, defaultUrl);
            return;
        }

        String redirectUrl = savedRequest.getRedirectUrl();
        redirectStrategy.sendRedirect(request, response, redirectUrl);
    }

    private String responseData(final OAuth2AuthenticationToken authentication, final String socialId) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> data = new HashMap<>();
        data.put("registrationId", authentication.getAuthorizedClientRegistrationId());
        data.put("id", socialId);
        data.put("name", authentication.getPrincipal().getAttribute("name"));
        return objectMapper.writeValueAsString(HttpResponse.of(HttpStatus.FOUND, "Email is null", data));
    }

    private String googleOrAnotherId(final OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        OAuth2User oauth2User = oAuth2AuthenticationToken.getPrincipal();

        final String registrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        if(registrationId.equals("google")) {
            return oauth2User.getAttribute("sub");
        }

        if(registrationId.equals("facebook")) {
            return oauth2User.getAttribute("id");
        }

        return Integer.toString(oauth2User.getAttribute("id"));
    }
}
