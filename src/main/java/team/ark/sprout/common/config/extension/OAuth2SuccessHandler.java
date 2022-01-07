package team.ark.sprout.common.config.extension;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import team.ark.sprout.adapter.in.web.GitHubAttributes;
import team.ark.sprout.domain.account.Account;
import team.ark.sprout.port.out.AccountRepository;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final RequestCache requestCache;

    public OAuth2SuccessHandler(ObjectMapper objectMapper, AccountRepository accountRepository) {
        this.objectMapper = objectMapper;
        this.accountRepository = accountRepository;
        this.requestCache = new HttpSessionRequestCache();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        updateOrSave(getOAuth2User(authentication));
        redirectToOriginDestination(request, response, authentication);
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

    private void redirectToOriginDestination(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = this.requestCache.getRequest(request, response);
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            this.requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        clearAuthenticationAttributes(request);
        String targetUrl = savedRequest.getRedirectUrl();
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
