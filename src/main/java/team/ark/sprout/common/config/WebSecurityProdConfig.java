package team.ark.sprout.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import team.ark.sprout.common.config.extension.GitHubOAuth2UserService;

@Profile("prod")
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityProdConfig extends WebSecurityConfigurerAdapter {
    private final GitHubOAuth2UserService gitHubOAuth2UserService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .requestMatchers(
                PathRequest.toH2Console(),
                PathRequest.toStaticResources().atCommonLocations()
            );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors(AbstractHttpConfigurer::disable)
            .headers(headers -> headers
                .frameOptions().disable()
            )
            .authorizeRequests(requests -> requests
                .mvcMatchers(HttpMethod.GET, "/docs/index.html").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(login -> login
                .userInfoEndpoint()
                .userService(gitHubOAuth2UserService)
            );
    }
}
