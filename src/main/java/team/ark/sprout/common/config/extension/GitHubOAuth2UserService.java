package team.ark.sprout.common.config.extension;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import team.ark.sprout.adapter.in.web.GitHubAttributes;
import team.ark.sprout.domain.account.Account;
import team.ark.sprout.port.out.AccountRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class GitHubOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService userService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = userService.loadUser(userRequest);
        updateOrSave(oAuth2User);
        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            getAttributes(oAuth2User),
            getNameAttributeKey(userRequest)
        );
    }

    private void updateOrSave(OAuth2User oAuth2User) {
        GitHubAttributes gitHubAttributes = attributesFrom(oAuth2User);
        accountRepository.save(
            accountRepository.findByUsername(gitHubAttributes.getUsername())
                .orElse(Account.from(gitHubAttributes))
                .update(gitHubAttributes)
        );
    }

    private GitHubAttributes attributesFrom(OAuth2User oAuth2User) {
        return objectMapper.convertValue(getAttributes(oAuth2User), GitHubAttributes.class);
    }

    private Map<String, Object> getAttributes(OAuth2User oAuth2User) {
        return oAuth2User.getAttributes();
    }

    private String getNameAttributeKey(OAuth2UserRequest userRequest) {
        return userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();
    }
}
