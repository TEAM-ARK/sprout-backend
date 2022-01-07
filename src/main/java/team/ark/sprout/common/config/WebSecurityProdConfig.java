package team.ark.sprout.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import team.ark.sprout.common.config.extension.OAuth2SuccessHandler;
import team.ark.sprout.common.config.extension.UrlMetadataSource;
import team.ark.sprout.common.config.extension.UrlResourceService;
import team.ark.sprout.common.config.extension.UrlResourcesMapFactoryBean;

@Profile("prod")
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityProdConfig extends WebSecurityConfigurerAdapter {
    private final UrlResourceService urlResourceService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors(AbstractHttpConfigurer::disable)
            .headers(headers -> headers
                .frameOptions().disable()
            )
            .oauth2Login(login -> login
                .successHandler(oAuth2SuccessHandler)
            )
            .sessionManagement()
            .maximumSessions(1);

        http
            .addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);
    }

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
        interceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        interceptor.setAccessDecisionManager(affirmativeBased());
        interceptor.setAuthenticationManager(authenticationManagerBean());
        return interceptor;
    }

    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getAccessDecisionVoters());
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        return List.of(new RoleVoter());
    }

    @Bean
    public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
        return new UrlMetadataSource(urlResourcesMapFactoryBean());
    }

    private UrlResourcesMapFactoryBean urlResourcesMapFactoryBean() {
        UrlResourcesMapFactoryBean urlResourcesMapFactoryBean = new UrlResourcesMapFactoryBean();
        urlResourcesMapFactoryBean.setUrlResourceService(urlResourceService);
        return urlResourcesMapFactoryBean;
    }
}
