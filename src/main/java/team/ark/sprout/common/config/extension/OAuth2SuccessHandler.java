package team.ark.sprout.common.config.extension;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import team.ark.sprout.adapter.in.web.GitHubAttributes;
import team.ark.sprout.domain.account.Account;
import team.ark.sprout.port.out.AccountRepository;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        updateOrSave(getOAuth2User(authentication));
        redirectToOriginDestination(request, response);
    }

    private OAuth2User getOAuth2User(Authentication authentication) {
        return ((OAuth2AuthenticationToken) authentication)
            .getPrincipal();
    }

    private void updateOrSave(OAuth2User oAuth2User) {
        GitHubAttributes gitHubAttributes = convertToGitHubAttributesFrom(oAuth2User);
        accountRepository.save(
            accountRepository.findByUsername(gitHubAttributes.getUsername())
                .orElse(Account.from(gitHubAttributes))
                .update(gitHubAttributes)
        );
    }

    private GitHubAttributes convertToGitHubAttributesFrom(OAuth2User oAuth2User) {
        return objectMapper.convertValue(oAuth2User.getAttributes(), GitHubAttributes.class);
    }

    private void redirectToOriginDestination(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
    }
}
